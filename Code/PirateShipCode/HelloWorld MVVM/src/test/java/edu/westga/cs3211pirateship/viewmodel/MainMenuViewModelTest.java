package edu.westga.cs3211pirateship.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;

class MainMenuViewModelTest {

    @Test
    void configureForRole_quartermaster_allPermissionsTrue() {
        MainMenuViewModel vm = new MainMenuViewModel();
        vm.configureForRole(UserRole.QUARTERMASTER);

        assertTrue(vm.canAddStockProperty().get());
        assertTrue(vm.canViewStockChangesProperty().get());
    }

    @Test
    void configureForRole_crewmate_addStockTrue_viewChangesFalse() {
        MainMenuViewModel vm = new MainMenuViewModel();
        vm.configureForRole(UserRole.CREWMATE);

        assertTrue(vm.canAddStockProperty().get());
        assertFalse(vm.canViewStockChangesProperty().get());
    }

    @Test
    void performingUser_set_get_returnsSame() {
        MainMenuViewModel vm = new MainMenuViewModel();
        User u = new User("id", "user", "pw", UserRole.CREWMATE, "Name");
        vm.setPerformingUser(u);
        assertSame(u, vm.getPerformingUser());
    }
}
