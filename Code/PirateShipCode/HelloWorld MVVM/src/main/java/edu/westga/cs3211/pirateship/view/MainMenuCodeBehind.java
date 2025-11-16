package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.MainMenuViewModel;
import edu.westga.cs3211.pirateship.model.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuCodeBehind {
	@FXML private Button addStockButton;

    @FXML private Button viewChangesButton;


    // ViewModel is injected from outside (e.g., by the login view or a DI/Factory)
    private MainMenuViewModel viewModel;

    @FXML
    public void initialize() {
        // No binding here. The ViewModel will be provided later via setViewModel(...)
    }

    // Called by whoever loads this view; accepts a pre-configured ViewModel
    public void setViewModel(MainMenuViewModel viewModel) {
        this.viewModel = viewModel;

        // bind view elements to the view model properties
        this.addStockButton.visibleProperty().bind(this.viewModel.canAddStockProperty());
        this.viewChangesButton.visibleProperty().bind(this.viewModel.canViewStockChangesProperty());
    }

}