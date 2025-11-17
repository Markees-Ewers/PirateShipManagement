package edu.westga.cs3211.pirateship.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class TestStockChange {

    @Test
    void testConstructorUpdatesQuantityAndGetters() {
        StockItem item = new StockItem("Apple", ItemCategory.FOOD, 5, "Hold");
        User user = new User("u1", "tester", "pw", UserRole.QUARTERMASTER, "T");

        long before = System.currentTimeMillis();
        StockChange change = new StockChange(item, 3, user, ChangeType.ADD_STOCK);
        long after = System.currentTimeMillis();

        assertNotNull(change.getId());
        assertFalse(change.getId().isEmpty());

        assertEquals(3, change.getChangeAmount());
        assertEquals(ChangeType.ADD_STOCK, change.getChangeType());
        assertEquals(user, change.getPerformedBy());
        assertEquals("Apple", change.getItemName());

        // timestamp in range
        long ts = change.getTimestamp();
        assertTrue(ts >= before && ts <= after + 1000, "timestamp should be around construction time");

        // quantity updated
        assertEquals(8, item.getCurrentQuantity());

        // previous and new quantity
        assertEquals(5, change.getPreviousQuantity());
        assertEquals(8, change.getNewQuantity());
    }

    @Test
    void testNegativeChangeDecreasesQuantity() {
        StockItem item = new StockItem("Barrel", ItemCategory.GROG, 10, "Hold");
        StockChange change = new StockChange(item, -4, null, ChangeType.REMOVE_STOCK);

        assertEquals(6, item.getCurrentQuantity());
        assertEquals(-4, change.getChangeAmount());
        assertEquals(ChangeType.REMOVE_STOCK, change.getChangeType());
        assertNull(change.getPerformedBy());

        assertEquals(10, change.getPreviousQuantity());
        assertEquals(6, change.getNewQuantity());
    }

    @Test
    void testIdsAreUnique() {
        StockItem i1 = new StockItem("X", ItemCategory.MUNITIONS, 2, "S1");
        StockItem i2 = new StockItem("Y", ItemCategory.MUNITIONS, 2, "S1");

        StockChange c1 = new StockChange(i1, 1, null, ChangeType.ADD_STOCK);
        StockChange c2 = new StockChange(i2, 1, null, ChangeType.ADD_STOCK);

        assertNotNull(c1.getId());
        assertNotNull(c2.getId());
        assertNotEquals(c1.getId(), c2.getId(), "each change should have unique id");
    }
}
