package edu.westga.cs3211.helloworld.model;

/**
 * Defines a person
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

	public User(String id, String username, String password, UserRole role, String name) {

		this.id = id;

		this.username = username;

		this.password = password;

		this.role = role;

		this.name = name;

	}
	// Getters and Setters

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}

	public String getName() {
		return name;
	}

}
