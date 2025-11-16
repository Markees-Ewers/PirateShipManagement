package edu.westga.cs3211pirateship.viewmodel;

import edu.westga.cs3211.pirateship.model.UserRole;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MainMenuViewModel {
	private final BooleanProperty canAddStock = new SimpleBooleanProperty(false);

	private final BooleanProperty canViewStockChanges = new SimpleBooleanProperty(false);

	public void configureForRole(UserRole role) {
		switch (role) {
		case QUARTERMASTER -> {
			canAddStock.set(true);
			canViewStockChanges.set(true);
		}
		case CREWMATE -> {
			canAddStock.set(true); // per user's request, crewmate can add stock
			canViewStockChanges.set(false);
		}
		default -> {
			canAddStock.set(false);
			canViewStockChanges.set(false);
		}
		}
	}

	public BooleanProperty canAddStockProperty() {

		return this.canAddStock;

	}

	public BooleanProperty canViewStockChangesProperty() {

		return this.canViewStockChanges;

	}

}