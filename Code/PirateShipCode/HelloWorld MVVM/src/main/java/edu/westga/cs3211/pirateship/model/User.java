package edu.westga.cs3211.pirateship.model;

/**
 * Defines a user within the system
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class User {
	private String id;
	private String username;
	private String password;
	private UserRole role;
	private String name;

	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param username the user name of the user
	 * @param password the password
	 * @param role the role
	 * @param name the name of the user
	 */
	public User(String id, String username, String password, UserRole role, String name) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	public UserRole getRole() {
		return this.role;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

}
