package edu.westga.cs3211.pirateship.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Very small in-memory repository for demo/testing purposes.
 */
public class InventoryRepository {
    private static final List<StockItem> items = new ArrayList<>();
    private static final List<StockChange> changes = new ArrayList<>();

    private InventoryRepository() {
        // static only
    }

    public static synchronized void addItem(StockItem item) {
        items.add(item);
    }

    public static synchronized void addChange(StockChange change) {
        changes.add(change);
    }

    public static synchronized List<StockItem> getItems() {
        return Collections.unmodifiableList(new ArrayList<>(items));
    }

    public static synchronized List<StockChange> getChanges() {
        return Collections.unmodifiableList(new ArrayList<>(changes));
    }

    public static synchronized StockItem findByName(String name) {
        if (name == null) {
            return null;
        }
        for (StockItem it : items) {
            if (it.getName().equalsIgnoreCase(name.trim())) {
                return it;
            }
        }
        return null;
    }
}
