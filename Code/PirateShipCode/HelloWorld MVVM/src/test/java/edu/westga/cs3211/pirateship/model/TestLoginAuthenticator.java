package edu.westga.cs3211.pirateship.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestLoginAuthenticator {

    @Test
    void testConstructorHasDefaultUsers() {
        LoginAuthenticator auth = new LoginAuthenticator();

        User q = auth.getUserByUsername("theQuarter");
        assertNotNull(q, "quartermaster user should exist");
        assertEquals("theQuarter", q.getUsername());
        assertEquals(UserRole.QUARTERMASTER, q.getRole());

        User c = auth.getUserByUsername("favCrewMate");
        assertNotNull(c, "crewmate user should exist");
        assertEquals("favCrewMate", c.getUsername());
        assertEquals(UserRole.CREWMATE, c.getRole());
    }

    @Test
    void testAuthenticateSuccessQuartermaster() {
        LoginAuthenticator auth = new LoginAuthenticator();
        UserRole role = auth.authenticate("theQuarter", "masterStockCounter");
        assertEquals(UserRole.QUARTERMASTER, role);
    }

    @Test
    void testAuthenticateSuccessCrewmate() {
        LoginAuthenticator auth = new LoginAuthenticator();
        UserRole role = auth.authenticate("favCrewMate", "bestCrewMate");
        assertEquals(UserRole.CREWMATE, role);
    }

    @Test
    void testAuthenticateWrongPasswordReturnsNull() {
        LoginAuthenticator auth = new LoginAuthenticator();
        assertNull(auth.authenticate("theQuarter", "wrong"), "wrong password should return null");
    }

    @Test
    void testAuthenticateUnknownUserReturnsNull() {
        LoginAuthenticator auth = new LoginAuthenticator();
        assertNull(auth.authenticate("unknownUser", "whatever"));
    }

    @Test
    void testAuthenticateNullUsernameReturnsNull() {
        LoginAuthenticator auth = new LoginAuthenticator();
        assertNull(auth.authenticate(null, "pw"));
    }

    @Test
    void testAuthenticateNullPasswordHandledGracefully() {
        LoginAuthenticator auth = new LoginAuthenticator();
        // stored password is non-null, so equals(null) should return false -> authenticate returns null
        assertNull(auth.authenticate("theQuarter", null));
    }

    @Test
    void testGetUserByUsernameReturnsNullForMissing() {
        LoginAuthenticator auth = new LoginAuthenticator();
        assertNull(auth.getUserByUsername(""));
        assertNull(auth.getUserByUsername("nope"));
    }
}
