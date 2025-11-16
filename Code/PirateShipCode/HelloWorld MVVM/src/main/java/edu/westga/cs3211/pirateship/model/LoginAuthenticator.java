package edu.westga.cs3211.pirateship.model;

import java.util.HashMap;

public class LoginAuthenticator {

	private HashMap<String, User> users = new HashMap<>();

	public LoginAuthenticator() {
		User quarterMaster = new User("12345", "theQuarter", "masterStockCounter", UserRole.QUARTERMASTER,
				"blackbeard");
		User crewMate1 = new User("54321", "favCrewMate", "bestCrewMate", UserRole.CREWMATE, "sanji");

		users.put(quarterMaster.getUsername(), quarterMaster);

		users.put(crewMate1.getUsername(), crewMate1);
	}

	public UserRole authenticate(String username, String password) {
		if (!users.containsKey(username)) {

			return null;

		}

		User user = users.get(username);

		if (user.getPassword().equals(password)) {

			return user.getRole();
		}

		return null;

	}
}