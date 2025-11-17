package edu.westga.cs3211pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.InventoryRepository;
import edu.westga.cs3211.pirateship.model.ItemCategory;
import edu.westga.cs3211.pirateship.model.StockItem;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAddStockViewModel {

    private InventoryRepository repo;
    private AddStockViewModel vm;
    private User testUser;

    @BeforeEach
    void setUp() {
        this.repo = InventoryRepository.getInstance();
        this.repo.clear();
        this.vm = new AddStockViewModel();
        this.testUser = new User("u1", "tester", "pw", UserRole.QUARTERMASTER, "Test User");
    }

    @AfterEach
    void tearDown() {
        this.repo.clear();
    }

    @Test
    void addStock_shouldFailWhenNameMissing() {
        this.vm.itemNameProperty().set("");
        this.vm.quantityProperty().set(5);
        this.vm.unitProperty().set("A1");
        this.vm.categoryProperty().set(ItemCategory.FOOD);

        boolean result = this.vm.addStock(this.testUser);

        assertFalse(result);
        assertEquals("Item name is required.", this.vm.errorMessageProperty().get());
        assertTrue(this.repo.getItems().isEmpty());
    }

    @Test
    void addStock_shouldFailWhenQuantityNonPositive() {
        this.vm.itemNameProperty().set("Apple");
        this.vm.quantityProperty().set(0);
        this.vm.unitProperty().set("A1");
        this.vm.categoryProperty().set(ItemCategory.FOOD);

        boolean result = this.vm.addStock(this.testUser);

        assertFalse(result);
        assertEquals("Quantity must be a positive integer.", this.vm.errorMessageProperty().get());
        assertTrue(this.repo.getItems().isEmpty());
    }

    @Test
    void addStock_shouldFailWhenCategoryMissing() {
        this.vm.itemNameProperty().set("Apple");
        this.vm.quantityProperty().set(3);
        this.vm.unitProperty().set("A1");
        this.vm.categoryProperty().set(null);

        boolean result = this.vm.addStock(this.testUser);

        assertFalse(result);
        assertEquals("Item category must be selected.", this.vm.errorMessageProperty().get());
        assertTrue(this.repo.getItems().isEmpty());
    }

    @Test
    void addStock_shouldFailWhenUnitMissing() {
        this.vm.itemNameProperty().set("Apple");
        this.vm.quantityProperty().set(3);
        this.vm.unitProperty().set("");
        this.vm.categoryProperty().set(ItemCategory.FOOD);

        boolean result = this.vm.addStock(this.testUser);

        assertFalse(result);
        assertEquals("Unit/location is required.", this.vm.errorMessageProperty().get());
        assertTrue(this.repo.getItems().isEmpty());
    }

    @Test
    void addStock_shouldAddNewItemWhenValid() {
        this.vm.itemNameProperty().set("Banana");
        this.vm.quantityProperty().set(10);
        this.vm.unitProperty().set("Deck");
        this.vm.categoryProperty().set(ItemCategory.GROG);

        boolean result = this.vm.addStock(this.testUser);

        assertTrue(result);
        assertEquals(1, this.repo.getItems().size());
        StockItem added = this.repo.getItems().get(0);
        assertEquals("Banana", added.getName());
        assertEquals(10, added.getCurrentQuantity());
        assertEquals(1, this.repo.getChanges().size());
        assertTrue(this.vm.successMessageProperty().get().contains("Added new item"));
    }

    @Test
    void addStock_shouldUpdateExistingItemWhenNameMatches() {
        // preload repository with an item
        StockItem existing = new StockItem("Orange", ItemCategory.FOOD, 5, "Hold");
        this.repo.addItem(existing);

        // attempt to add same item (case-insensitive, trimmed)
        this.vm.itemNameProperty().set("  orange  ");
        this.vm.quantityProperty().set(3);
        this.vm.unitProperty().set("Hold");
        this.vm.categoryProperty().set(ItemCategory.FOOD);

        boolean result = this.vm.addStock(this.testUser);

        assertTrue(result);
        assertEquals(1, this.repo.getItems().size());
        StockItem after = this.repo.getItems().get(0);
        assertEquals(8, after.getCurrentQuantity()); // 5 + 3
        assertEquals(1, this.repo.getChanges().size());
        assertTrue(this.vm.successMessageProperty().get().contains("Updated existing item"));
    }
}
