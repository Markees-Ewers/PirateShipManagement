package edu.westga.cs3211pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.LoginAuthenticator;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class ViewModel.
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class LoginPageViewModel {
	private StringProperty userNameProperty;
	private StringProperty passwordProperty;
	private LoginAuthenticator authenticator;
	private User authenticatedUser; 

	/**
	 * Instantiates a new login page view model.
	 */
	public LoginPageViewModel() {
		this.userNameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
		this.authenticator = new LoginAuthenticator();
	}

	/**
	 * User name property.
	 *
	 * @return the string property
	 */
	public StringProperty userNameProperty() {
		return this.userNameProperty;
	}

	/**
	 * Password property.
	 *
	 * @return the string property
	 */
	public StringProperty passwordProperty() {
		return this.passwordProperty;
	}

	/**
	 * Authenticate user.
	 *
	 * @return the login result
	 */
	public LoginResult authenticateUser() {
		String username = this.userNameProperty.get();
		String password = this.passwordProperty.get();
		UserRole role = this.authenticator.authenticate(username, password);
		if (role == null) {
		    this.authenticatedUser = null;
		    return LoginResult.FAILURE;
		}

		this.authenticatedUser = this.authenticator.getUserByUsername(username);
		if (role == UserRole.QUARTERMASTER) {
		    return LoginResult.SUCCESS_QUARTERMASTER;
		} else {
	        return LoginResult.SUCCESS_CREWMATE;
	    }
	}

	/**
	 * Returns the User object for the last successful authentication, or null
	 * if authentication has not succeeded.
	 *
	 * @return the authenticated user
	 */
	public User getAuthenticatedUser() {
		return this.authenticatedUser;
	}

}