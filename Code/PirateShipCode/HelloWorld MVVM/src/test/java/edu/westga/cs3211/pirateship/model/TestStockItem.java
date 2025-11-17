package edu.westga.cs3211.pirateship.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestStockItem {

    @Test
    void testConstructorAndGetters() {
        StockItem item = new StockItem("Apple", ItemCategory.FOOD, 5, "A1");

        assertEquals("Apple", item.getName());
        assertEquals(ItemCategory.FOOD, item.getCategory());
        assertEquals(5, item.getCurrentQuantity());
        assertEquals("A1", item.getUnit());

        assertNotNull(item.getId());
        assertFalse(item.getId().isEmpty());
    }

    @Test
    void testSetCurrentQuantity() {
        StockItem item = new StockItem("Barrel", ItemCategory.SAILING_SUPPLIES, 2, "Hold");
        assertEquals(2, item.getCurrentQuantity());

        item.setCurrentQuantity(10);
        assertEquals(10, item.getCurrentQuantity());

        // allow negative (class doesn't prevent it) — assert it stores value
        item.setCurrentQuantity(-4);
        assertEquals(-4, item.getCurrentQuantity());
    }

    @Test
    void testIdsAreUnique() {
        StockItem first = new StockItem("X", ItemCategory.GROG, 1, "Cupboard");
        StockItem second = new StockItem("Y", ItemCategory.GROG, 1, "Cupboard");

        assertNotNull(first.getId());
        assertNotNull(second.getId());
        assertNotEquals(first.getId(), second.getId(), "Each StockItem should have a unique id");
    }
}