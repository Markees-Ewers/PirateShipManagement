package edu.westga.cs3211pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The Class MainMenuViewModel.
 * @author CS 3211
 * @version Fall 2025
 */
public class MainMenuViewModel {
	private final BooleanProperty canAddStock = new SimpleBooleanProperty(false);

	private final BooleanProperty canViewStockChanges = new SimpleBooleanProperty(false);

	private User performingUser;

	/**
	 * Configure for role.
	 *
	 * @param role the role
	 */
	public void configureForRole(UserRole role) {
		if (role == UserRole.QUARTERMASTER) {
	        this.canAddStock.set(true);
	        this.canViewStockChanges.set(true);
	        return;
	    } 
	        this.canAddStock.set(true);
	        this.canViewStockChanges.set(false);
	}

	/**
	 * Sets the performing user.
	 *
	 * @param user the new performing user
	 */
	public void setPerformingUser(User user) {
		this.performingUser = user;
	}

	/**
	 * Gets the performing user.
	 *
	 * @return the performing user
	 */
	public User getPerformingUser() {
		return this.performingUser;
	}

	/**
	 * Can add stock property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty canAddStockProperty() {

		return this.canAddStock;

	}

	/**
	 * Can view stock changes property.
	 *
	 * @return the boolean property
	 */
	public BooleanProperty canViewStockChangesProperty() {

		return this.canViewStockChanges;
	}

}