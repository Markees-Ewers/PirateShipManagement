package edu.westga.cs3211pirateship.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;

class LoginPageViewModelTest {

    private LoginPageViewModel vm;

    @BeforeEach
    void setup() {
        this.vm = new LoginPageViewModel();
    }

    @Test
    void initialAuthenticatedUserIsNull() {
        assertNull(this.vm.getAuthenticatedUser());
    }

    @Test
    void successfulQuartermasterLogin() {
        this.vm.userNameProperty().set("theQuarter");
        this.vm.passwordProperty().set("masterStockCounter");

        LoginResult result = this.vm.authenticateUser();
        assertEquals(LoginResult.SUCCESS_QUARTERMASTER, result);

        User auth = this.vm.getAuthenticatedUser();
        assertNotNull(auth);
        assertEquals("theQuarter", auth.getUsername());
        assertEquals(UserRole.QUARTERMASTER, auth.getRole());
    }

    @Test
    void successfulCrewmateLogin() {
        this.vm.userNameProperty().set("favCrewMate");
        this.vm.passwordProperty().set("bestCrewMate");

        LoginResult result = this.vm.authenticateUser();
        assertEquals(LoginResult.SUCCESS_CREWMATE, result);

        User auth = this.vm.getAuthenticatedUser();
        assertNotNull(auth);
        assertEquals("favCrewMate", auth.getUsername());
        assertEquals(UserRole.CREWMATE, auth.getRole());
    }

    @Test
    void failedLoginWrongPasswordClearsAuthenticatedUser() {
        this.vm.userNameProperty().set("theQuarter");
        this.vm.passwordProperty().set("wrong");

        LoginResult result = this.vm.authenticateUser();
        assertEquals(LoginResult.FAILURE, result);
        assertNull(this.vm.getAuthenticatedUser());
    }

    @Test
    void failedLoginUnknownUser() {
        this.vm.userNameProperty().set("noSuchUser");
        this.vm.passwordProperty().set("nope");

        LoginResult result = this.vm.authenticateUser();
        assertEquals(LoginResult.FAILURE, result);
        assertNull(this.vm.getAuthenticatedUser());
    }

    @Test
    void nullInputsReturnFailureAndNoAuthenticatedUser() {
        this.vm.userNameProperty().set(null);
        this.vm.passwordProperty().set(null);

        LoginResult result = this.vm.authenticateUser();
        assertEquals(LoginResult.FAILURE, result);
        assertNull(this.vm.getAuthenticatedUser());
    }

    @Test
    void successiveAuthenticate_callsUpdateAuthenticatedUser() {
        // successful first
        this.vm.userNameProperty().set("theQuarter");
        this.vm.passwordProperty().set("masterStockCounter");
        assertEquals(LoginResult.SUCCESS_QUARTERMASTER, this.vm.authenticateUser());
        assertNotNull(this.vm.getAuthenticatedUser());

        // now fail
        this.vm.userNameProperty().set("theQuarter");
        this.vm.passwordProperty().set("wrong");
        assertEquals(LoginResult.FAILURE, this.vm.authenticateUser());
        assertNull(this.vm.getAuthenticatedUser());

        // then succeed as different user
        this.vm.userNameProperty().set("favCrewMate");
        this.vm.passwordProperty().set("bestCrewMate");
        assertEquals(LoginResult.SUCCESS_CREWMATE, this.vm.authenticateUser());
        assertNotNull(this.vm.getAuthenticatedUser());
        assertEquals("favCrewMate", this.vm.getAuthenticatedUser().getUsername());
    }
    
    @Test
    void wrongUserNameCorrectPasswordShouldFail() {
		this.vm.userNameProperty().set("wrongUser");
		this.vm.passwordProperty().set("masterStockCounter");

		LoginResult result = this.vm.authenticateUser();
		assertEquals(LoginResult.FAILURE, result);
		assertNull(this.vm.getAuthenticatedUser());
	}
}
