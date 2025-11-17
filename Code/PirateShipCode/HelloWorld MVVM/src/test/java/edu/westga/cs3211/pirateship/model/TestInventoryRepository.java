package edu.westga.cs3211.pirateship.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestInventoryRepository {

    private InventoryRepository repo;

    @BeforeEach
    void setUp() {
        this.repo = InventoryRepository.getInstance();
        this.repo.clear();
    }

    @Test
    void testSingletonInstanceIsSame() {
        InventoryRepository other = InventoryRepository.getInstance();
        assertSame(this.repo, other, "getInstance should always return the same singleton instance");
    }

    @Test
    void testAddItemAndGetItemsUnmodifiableAndSnapshotBehavior() {
        StockItem item = new StockItem("Apple", ItemCategory.FOOD, 5, "A1");
        this.repo.addItem(item);

        List<StockItem> snapshot = this.repo.getItems();
        assertEquals(1, snapshot.size());
        assertSame(item, snapshot.get(0));

        // returned list should be unmodifiable
        assertThrows(UnsupportedOperationException.class, () -> snapshot.add(new StockItem("X", ItemCategory.GROG, 1, "U")));

        // adding another item to repo after taking snapshot should not change the snapshot
        StockItem second = new StockItem("Barrel", ItemCategory.SAILING_SUPPLIES, 2, "Hold");
        this.repo.addItem(second);
        List<StockItem> later = this.repo.getItems();
        assertEquals(2, later.size());
        assertEquals(1, snapshot.size(), "previous snapshot must remain unchanged since repo returns a copy");
    }

    @Test
    void testAddChangeAndGetChangesUnmodifiable() {
        StockItem item = new StockItem("Apple", ItemCategory.FOOD, 5, "A1");
        this.repo.addItem(item);

        StockChange change = new StockChange(item, 3, null, ChangeType.ADD_STOCK);
        this.repo.addChange(change);

        List<StockChange> changes = this.repo.getChanges();
        assertEquals(1, changes.size());
        assertSame(change, changes.get(0));

        assertThrows(UnsupportedOperationException.class, () -> changes.remove(0));
    }

    @Test
    void testFindByNameNullAndNotFound() {
        assertNull(this.repo.findByName(null));
        assertNull(this.repo.findByName("not-there"));
    }

    @Test
    void testFindByNameTrimAndCaseInsensitive() {
        StockItem banana = new StockItem("Banana", ItemCategory.FOOD, 3, "U1");
        this.repo.addItem(banana);

        // search with different case and surrounding spaces
        assertSame(banana, this.repo.findByName(" banana "));
        assertSame(banana, this.repo.findByName("BANANA"));
        assertSame(banana, this.repo.findByName("BaNaNa"));
    }

    @Test
    void testFindByNameSkipsNonMatchingItemsAndFindsLaterMatch() {
        StockItem orange = new StockItem("Orange", ItemCategory.FOOD, 2, "U");
        StockItem banana = new StockItem("Banana", ItemCategory.FOOD, 3, "U1");
        this.repo.addItem(orange);
        this.repo.addItem(banana);

        // first item should not match, loop must continue and then find the second
        StockItem found = this.repo.findByName(" banana ");
        assertSame(banana, found, "findByName should return the later matching item after skipping non-matches");
    }

    @Test
    void testClearEmptiesBothLists() {
        StockItem item = new StockItem("Apple", ItemCategory.FOOD, 5, "A1");
        this.repo.addItem(item);
        StockChange change = new StockChange(item, 1, null, ChangeType.ADD_STOCK);
        this.repo.addChange(change);

        assertFalse(this.repo.getItems().isEmpty());
        assertFalse(this.repo.getChanges().isEmpty());

        this.repo.clear();

        assertTrue(this.repo.getItems().isEmpty(), "items should be empty after clear");
        assertTrue(this.repo.getChanges().isEmpty(), "changes should be empty after clear");
    }
}