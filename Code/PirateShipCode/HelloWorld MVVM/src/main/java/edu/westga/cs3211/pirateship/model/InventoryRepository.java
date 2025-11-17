package edu.westga.cs3211.pirateship.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Very small in-memory repository for demo/testing purposes.
 * @author Markees Ewers
 * @version Fall 2025
 */
public final class InventoryRepository {
    
    // Singleton instance for easy global access (keeps instance-style methods)
    private static final InventoryRepository INSTANCE = new InventoryRepository();

    /** The Constant items. */
    private List<StockItem> items = new ArrayList<>();
    
    /** The Constant changes. */
    private List<StockChange> changes = new ArrayList<>();

    /**
     * Instantiates a new inventory repository.
     */
    private InventoryRepository() {
        // private
    }

    /**
     * Get the singleton repository instance.
     * Use this when code previously relied on static access.
     *
     * @return single instance of InventoryRepository
     */
    public static InventoryRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Adds the item.
     *
     * @param item the item
     */
    public  void addItem(StockItem item) {
        this.items.add(item);
    }

    /**
     * Adds the change.
     *
     * @param change the change
     */
    public void addChange(StockChange change) {
        this.changes.add(change);
    }

    /**
     * Gets the items.
     *
     * @return the items
     */
    public List<StockItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(this.items));
    }

    /**
     * Gets the changes.
     *
     * @return the changes
     */
    public List<StockChange> getChanges() {
        return Collections.unmodifiableList(new ArrayList<>(this.changes));
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the stock item
     */
    public StockItem findByName(String name) {
        if (name == null) {
            return null;
        }
        for (StockItem it : this.items) {
            if (it.getName().equalsIgnoreCase(name.trim())) {
                return it;
            }
        }
        return null;
    }

    /**
     * Clear repository contents (for testing).
     */
    public void clear() {
        this.items.clear();
        this.changes.clear();
    }
}