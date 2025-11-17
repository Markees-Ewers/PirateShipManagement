package edu.westga.cs3211pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.InventoryRepository;
import edu.westga.cs3211.pirateship.model.StockChange;
import edu.westga.cs3211.pirateship.model.StockItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewStockViewModel {
    private final InventoryRepository repo;
    private final ObservableList<StockItem> inventory = FXCollections.observableArrayList();
    private final ObservableList<StockChange> changes = FXCollections.observableArrayList();

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ViewStockViewModel(InventoryRepository repo) {
        this.repo = repo;
    }

    public ObservableList<StockItem> getInventory() {
        return this.inventory;
    }

    public ObservableList<StockChange> getChanges() {
        return this.changes;
    }

    public void refresh() {
        List<StockItem> items = this.repo.getItems();
        List<StockChange> chgs = this.repo.getChanges();
        this.inventory.setAll(items);
        this.changes.setAll(chgs);
    }

    public String formatStockItem(StockItem item) {
        if (item == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(item.getName()).append('\n');
        sb.append("Category: ").append(item.getCategory()).append('\n');
        sb.append("Quantity: ").append(item.getCurrentQuantity()).append('\n');
        sb.append("Unit: ").append(item.getUnit()).append('\n');
        return sb.toString();
    }

    public String formatStockChange(StockChange change) {
        if (change == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // convert epoch millis to LocalDateTime in system default zone
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(change.getTimestamp()), ZoneId.systemDefault());
        sb.append("Time: ").append(dtf.format(time)).append('\n');
        sb.append("Item: ").append(change.getItem().getName()).append('\n');
        sb.append("Change: ").append(change.getChangeAmount()).append('\n');
        sb.append("Type: ").append(change.getChangeType()).append('\n');
        sb.append("By: ").append(change.getPerformedBy() == null ? "(unknown)" : change.getPerformedBy().getName()).append('\n');
        return sb.toString();
    }
}