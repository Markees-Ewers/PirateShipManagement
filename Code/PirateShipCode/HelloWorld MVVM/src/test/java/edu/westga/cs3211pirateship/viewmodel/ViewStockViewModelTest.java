package edu.westga.cs3211pirateship.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.InventoryRepository;
import edu.westga.cs3211.pirateship.model.ItemCategory;
import edu.westga.cs3211.pirateship.model.StockChange;
import edu.westga.cs3211.pirateship.model.StockItem;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;

class ViewStockViewModelTest {

    private InventoryRepository repo;
    private ViewStockViewModel vm;

    @BeforeEach
    void setup() {
        this.repo = InventoryRepository.getInstance();
        this.repo.clear();
        this.vm = new ViewStockViewModel(this.repo);
    }

    @Test
    void refresh_populatesInventoryAndChangesFromRepository() {
        StockItem s1 = new StockItem("Apple", ItemCategory.FOOD, 5, "Hold");
        StockItem s2 = new StockItem("Rope", ItemCategory.SAILING_SUPPLIES, 2, "Bin");
        this.repo.addItem(s1);
        this.repo.addItem(s2);

        StockChange c1 = new StockChange(s1, 3, null, null);
        this.repo.addChange(c1);

        // precondition: vm lists empty
        assertTrue(this.vm.getInventory().isEmpty());
        assertTrue(this.vm.getChanges().isEmpty());

        this.vm.refresh();

        List<StockItem> inv = this.vm.getInventory();
        List<StockChange> changes = this.vm.getChanges();

        assertEquals(2, inv.size());
        assertTrue(inv.contains(s1));
        assertTrue(inv.contains(s2));

        assertEquals(1, changes.size());
        assertSame(c1, changes.get(0));
    }

    @Test
    void formatStockItem_null_returnsEmptyString() {
        assertEquals("", this.vm.formatStockItem(null));
    }

    @Test
    void formatStockItem_outputsAllFields() {
        StockItem s = new StockItem("Banana", ItemCategory.GROG, 10, "Deck");
        String out = this.vm.formatStockItem(s);
        assertTrue(out.contains("Name: Banana"));
        assertTrue(out.contains("Category: GROG"));
        assertTrue(out.contains("Quantity: 10"));
        assertTrue(out.contains("Unit: Deck"));
    }

    @Test
    void formatStockChange_null_returnsEmptyString() {
        assertEquals("", this.vm.formatStockChange(null));
    }

    @Test
    void formatStockChange_withPerformedByAndTimestamp_formatsCorrectly() {
        StockItem s = new StockItem("Fish", ItemCategory.FOOD, 4, "Hold");
        User u = new User("u1", "sailor", "pw", UserRole.CREWMATE, "Sailor Sam");
        // create change with known timestamp by construction and then read
        StockChange change = new StockChange(s, 5, u, null);

        String out = this.vm.formatStockChange(change);
        assertTrue(out.contains("Item: Fish"));
        assertTrue(out.contains("Change: 5"));
        assertTrue(out.contains("By: Sailor Sam"));

        // verify time formatting roughly matches the timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(change.getTimestamp()), ZoneId.systemDefault());
        String expectedTime = dtf.format(time);
        assertTrue(out.contains("Time: " + expectedTime));
    }

    @Test
    void formatStockChange_performedByNull_showsUnknown() {
        StockItem s = new StockItem("Anchor", ItemCategory.SAILING_SUPPLIES, 1, "Deck");
        StockChange change = new StockChange(s, 0, null, null);
        String out = this.vm.formatStockChange(change);
        assertTrue(out.contains("By: (unknown)"));
    }
}
