package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.AddStockViewModel;
import edu.westga.cs3211.pirateship.model.ItemCategory;
import edu.westga.cs3211.pirateship.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;

import java.io.IOException;

/**
 * Code-behind for the Add Stock view.
 */
public class AddStockCodeBehind {
    @FXML
    private TextField itemNameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField unitTextField;

    @FXML
    private ComboBox<ItemCategory> categoryComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label successLabel;

    private final AddStockViewModel viewModel;

    // performing user is set by the caller (MainMenuCodeBehind)
    private User performingUser;

    public AddStockCodeBehind() {
        this.viewModel = new AddStockViewModel();
    }

    @FXML
    void initialize() {
        this.bindComponentsToViewModel();
    }

    private void bindComponentsToViewModel() {
        // Bind text fields
        this.itemNameTextField.textProperty().bindBidirectional(this.viewModel.itemNameProperty());
        // Quantity is an IntegerProperty; use a NumberStringConverter to bind to text
        this.quantityTextField.textProperty().bindBidirectional(this.viewModel.quantityProperty(), new NumberStringConverter());
        this.unitTextField.textProperty().bindBidirectional(this.viewModel.unitProperty());

        // Populate category combobox and bind selection
        this.categoryComboBox.setItems(FXCollections.observableArrayList(ItemCategory.values()));
        this.categoryComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> this.viewModel.categoryProperty().set(newV));
        // reflect viewmodel changes back to combobox selection
        this.viewModel.categoryProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) {
                this.categoryComboBox.getSelectionModel().clearSelection();
            } else {
                this.categoryComboBox.getSelectionModel().select(newV);
            }
        });

        // Bind messages
        this.errorLabel.textProperty().bind(this.viewModel.errorMessageProperty());
        this.successLabel.textProperty().bind(this.viewModel.successMessageProperty());

        // Enable Add button only when all required fields are valid
        BooleanBinding allFieldsValid = Bindings.createBooleanBinding(() -> {
            String name = this.viewModel.itemNameProperty().get();
            String unitVal = this.viewModel.unitProperty().get();
            ItemCategory cat = this.viewModel.categoryProperty().get();
            int qty = this.viewModel.quantityProperty().get();
            boolean nameOk = name != null && !name.trim().isEmpty();
            boolean unitOk = unitVal != null && !unitVal.trim().isEmpty();
            boolean qtyOk = qty > 0;
            boolean catOk = cat != null;
            return nameOk && unitOk && qtyOk && catOk;
        }, this.viewModel.itemNameProperty(), this.viewModel.unitProperty(), this.viewModel.categoryProperty(), this.viewModel.quantityProperty());

        this.addButton.disableProperty().bind(allFieldsValid.not());

        // Wire add button
        this.addButton.setOnAction(evt -> this.handleAdd());
    }

    private void handleAdd() {
        boolean ok = this.viewModel.addStock(this.performingUser);
        if (ok) {
            // clear inputs on success
            this.itemNameTextField.clear();
            this.quantityTextField.setText("0");
            this.unitTextField.clear();
            this.categoryComboBox.getSelectionModel().clearSelection();
        }
    }

    // Called by MainMenuCodeBehind after loading the FXML so the view knows who is performing the action
    public void setPerformingUser(User user) {
        this.performingUser = user;
    }
}