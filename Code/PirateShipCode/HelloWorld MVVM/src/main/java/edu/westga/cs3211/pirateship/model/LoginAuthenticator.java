package edu.westga.cs3211.pirateship.model;

import java.util.HashMap;

/**
 * The Class LoginAuthenticator.
 * @author Markees Ewers
 * @version Fall 2025
 */
public class LoginAuthenticator {

	private HashMap<String, User> users = new HashMap<>();

	/**
	 * Instantiates a new login authenticator.
	 */
	public LoginAuthenticator() {
		User quarterMaster = new User("12345", "theQuarter", "masterStockCounter", UserRole.QUARTERMASTER,
				"blackbeard");
		User crewMate1 = new User("54321", "favCrewMate", "bestCrewMate", UserRole.CREWMATE, "sanji");

		this.users.put(quarterMaster.getUsername(), quarterMaster);

		this.users.put(crewMate1.getUsername(), crewMate1);
	}

	/**
	 * Authenticate.
	 *
	 * @param username the user name to be authenticated
	 * @param password the password
	 * @return the user role
	 */
	public UserRole authenticate(String username, String password) {
		if (!this.users.containsKey(username)) {

			return null;

		}

		User user = this.users.get(username);

		if (user.getPassword().equals(password)) {

			return user.getRole();
		}

		return null;

	}

	/**
	 * Lookup a User by user name (may return null). This allows callers to obtain
	 * the full User object after an authentication check.
	 *
	 * @param username the user name
	 * @return the user by user name
	 */
	public User getUserByUsername(String username) {
		return this.users.get(username);
	}
}