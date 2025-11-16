package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.MainMenuViewModel;
import edu.westga.cs3211.pirateship.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuCodeBehind {
	@FXML private Button addStockButton;

    @FXML private Button viewChangesButton;


    // ViewModel is injected from outside (e.g., by the login view or a DI/Factory)
    private MainMenuViewModel viewModel;

    @FXML
    public void initialize() {
        // No binding here. The ViewModel will be provided later via setViewModel(...)
        // Ensure the Add Stock button is always wired to open the Add Stock page so
        // navigation works even if setViewModel(...) was not called by the loader.
        this.addStockButton.setOnAction(evt -> this.openAddStockPage());
    }

    // Called by whoever loads this view; accepts a pre-configured ViewModel
    public void setViewModel(MainMenuViewModel viewModel) {
        this.viewModel = viewModel;

        // bind view elements to the view model properties
        this.addStockButton.visibleProperty().bind(this.viewModel.canAddStockProperty());
        this.viewChangesButton.visibleProperty().bind(this.viewModel.canViewStockChangesProperty());

        // wire button action to navigate to AddStock view
        this.addStockButton.setOnAction(evt -> this.openAddStockPage());
    }

    private void openAddStockPage() {
        if (this.viewModel == null) {
            // proceed even if viewModel is not available; performing user may not be known
        }
        User user = this.viewModel == null ? null : this.viewModel.getPerformingUser();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStock.fxml"));
            Parent root = loader.load();
            AddStockCodeBehind controller = loader.getController();
            if (controller != null && user != null) {
                controller.setPerformingUser(user);
            }
            Stage stage = (Stage) this.addStockButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

}