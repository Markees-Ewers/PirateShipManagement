package edu.westga.cs3211.pirateship.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUser {

    @Test
    void testConstructorAndGetters() {
        User user = new User("id1", "alice", "secret", UserRole.CREWMATE, "Alice Smith");

        assertEquals("id1", user.getId());
        assertEquals("alice", user.getUsername());
        assertEquals("secret", user.getPassword());
        assertEquals(UserRole.CREWMATE, user.getRole());
        assertEquals("Alice Smith", user.getName());
    }

    @Test
    void testNullFields() {
        User user = new User(null, null, null, null, null);

        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertNull(user.getName());
    }

    @Test
    void testDifferentUsers() {
        User a = new User("idA", "bob", "p1", UserRole.QUARTERMASTER, "Bob");
        User b = new User("idB", "bob", "p1", UserRole.QUARTERMASTER, "Bob");

        // ids differ but other getters may be equal
        assertNotEquals(a.getId(), b.getId());
        assertEquals(a.getUsername(), b.getUsername());
        assertEquals(a.getPassword(), b.getPassword());
        assertEquals(a.getRole(), b.getRole());
        assertEquals(a.getName(), b.getName());
    }
}
