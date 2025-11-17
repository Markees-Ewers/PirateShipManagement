package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.MainMenuViewModel;
import edu.westga.cs3211.pirateship.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import java.io.IOException;

/**
 * The Class MainMenuCodeBehind.
 * @author CS 3211
 * @version Fall 2025
 */
public class MainMenuCodeBehind {
	@FXML private Button addStockButton;

    @FXML private Button viewChangesButton;

    @FXML private Button backButton;

    private MainMenuViewModel viewModel;

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        // No binding here. The ViewModel will be provided later via setViewModel(...)
        // Ensure the Add Stock button is always wired to open the Add Stock page so
        // navigation works even if setViewModel(...) was not called by the loader.
        this.addStockButton.setOnAction(evt -> this.openAddStockPage());
        this.viewChangesButton.setOnAction(evt -> this.openViewStockChangesPage());
    }

    /**
     * Sets the view model.
     *
     * @param viewModel the new view model
     */
    public void setViewModel(MainMenuViewModel viewModel) {
        this.viewModel = viewModel;

        // bind view elements to the view model properties
        this.addStockButton.visibleProperty().bind(this.viewModel.canAddStockProperty());
        this.viewChangesButton.visibleProperty().bind(this.viewModel.canViewStockChangesProperty());

        // wire button action to navigate to AddStock view
        this.addStockButton.setOnAction(evt -> this.openAddStockPage());
        // wire view changes button to open the View Stock Changes page
        this.viewChangesButton.setOnAction(evt -> this.openViewStockChangesPage());
    }

    @FXML
    void handleBack() {
        NavigationManager.goBack();
    }

    private void openAddStockPage() {
        if (this.viewModel == null) {
         return;
        }
        User user = this.viewModel.getPerformingUser();
        if (user == null) {
		 return;
		}	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStock.fxml"));
            Object controllerObj = NavigationManager.navigateTo(loader);
            AddStockCodeBehind controller = null;
            if (controllerObj instanceof AddStockCodeBehind c) {
                controller = c;
            }
            if (controller != null && user != null) {
                controller.setPerformingUser(user);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void openViewStockChangesPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStockChanges.fxml"));
            NavigationManager.navigateTo(loader);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

}