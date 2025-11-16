package edu.westga.cs3211pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.ChangeType;
import edu.westga.cs3211.pirateship.model.InventoryRepository;
import edu.westga.cs3211.pirateship.model.ItemCategory;
import edu.westga.cs3211.pirateship.model.StockChange;
import edu.westga.cs3211.pirateship.model.StockItem;
import edu.westga.cs3211.pirateship.model.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for the Add Stock view.
 */
public class AddStockViewModel {
    private final StringProperty itemName = new SimpleStringProperty("");
    private final IntegerProperty quantity = new SimpleIntegerProperty(0);
    private final StringProperty unit = new SimpleStringProperty("");
    // default to null so UI must explicitly select a category
    private final SimpleObjectProperty<ItemCategory> category = new SimpleObjectProperty<>(null);

    private final StringProperty errorMessage = new SimpleStringProperty("");
    private final StringProperty successMessage = new SimpleStringProperty("");
    
    public StringProperty itemNameProperty() {
        return this.itemName;
    }

    public IntegerProperty quantityProperty() {
        return this.quantity;
    }

    public StringProperty unitProperty() {
        return this.unit;
    }

    public SimpleObjectProperty<ItemCategory> categoryProperty() {
        return this.category;
    }

    public StringProperty errorMessageProperty() {
        return this.errorMessage;
    }

    public StringProperty successMessageProperty() {
        return this.successMessage;
    }

    /**
     * Validate inputs and add or update stock.
     * For simplicity this method requires a performing user.
     * Returns true on success, false on error.
     */
    public boolean addStock(User performedBy) {
        // clear previous messages
        this.errorMessage.set("");
        this.successMessage.set("");

        String name = this.itemName.get();
        if (name == null || name.trim().isEmpty()) {
            this.errorMessage.set("Item name is required.");
            return false;
        }
        name = name.trim();

        int qty = this.quantity.get();
        if (qty <= 0) {
            this.errorMessage.set("Quantity must be a positive integer.");
            return false;
        }

        ItemCategory cat = this.category.get();
        if (cat == null) {
            this.errorMessage.set("Item category must be selected.");
            return false;
        }

        String unitVal = this.unit.get();
        if (unitVal == null || unitVal.trim().isEmpty()) {
           this.errorMessage.set("Unit/location is required.");
           return false;
        }
        unitVal = unitVal.trim();

        // Check if item already exists
        StockItem existing = InventoryRepository.findByName(name);
        StockItem item;
        if (existing != null) {
            // If item exists, ensure we don't create a new item with missing fields
            item = existing;
            // Create a stock change (ADD_STOCK)
            StockChange change = new StockChange(item, qty, performedBy, ChangeType.ADD_STOCK);
            InventoryRepository.addChange(change);
            this.successMessage.set("Updated existing item: " + item.getName());
            return true;
        }

        // create new item and add to repository
        item = new StockItem(name, cat, qty, unitVal);
        InventoryRepository.addItem(item);
        StockChange change = new StockChange(item, qty, performedBy, ChangeType.ADD_STOCK);
        InventoryRepository.addChange(change);

        this.successMessage.set("Added new item: " + item.getName());
        return true;
    }
}