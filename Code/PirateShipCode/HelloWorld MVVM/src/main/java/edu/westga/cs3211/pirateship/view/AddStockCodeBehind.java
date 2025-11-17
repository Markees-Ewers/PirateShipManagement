package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.AddStockViewModel;
import edu.westga.cs3211.pirateship.model.ItemCategory;
import edu.westga.cs3211.pirateship.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javafx.util.converter.IntegerStringConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringBinding;

import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;

/**
 * Code-behind for the Add Stock view.
 * 
 * @author Markees Ewers
 * @version Fall 2025
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
	private Button backButton;

	@FXML
	private Label errorLabel;

	@FXML
	private Label successLabel;

	private final AddStockViewModel viewModel;

	// performing user is set by the caller (MainMenuCodeBehind)
	private User performingUser;

	/**
	 * Instantiates a new adds the stock code behind.
	 */
	public AddStockCodeBehind() {
		this.viewModel = new AddStockViewModel();
	}

	@FXML
	void initialize() {
		this.bindComponentsToViewModel();
	}

	private void bindComponentsToViewModel() {
		this.bindTextFields();
		this.bindQuantityField();
		this.bindCategoryComboBox();
		this.bindErrorLabel();
		this.bindSuccessLabel();
		this.bindAddButton();
		this.bindButtonActions();
	}

	private void bindTextFields() {
		this.itemNameTextField.textProperty()
				.bindBidirectional(this.viewModel.itemNameProperty());
		this.unitTextField.textProperty()
				.bindBidirectional(this.viewModel.unitProperty());
	}

	private void bindQuantityField() {
		UnaryOperator<TextFormatter.Change> integerFilter = change -> {
		    String newText = change.getControlNewText();
		    if (newText.matches("\\d*")) {
		        return change;
		    }
		    return null;
		};

		TextFormatter<Integer> formatter =
				new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter);
		this.quantityTextField.setTextFormatter(formatter);
		// Bind IntegerProperty to formatter value
		formatter.valueProperty()
				.bindBidirectional(this.viewModel.quantityProperty().asObject());
	}

	private void bindCategoryComboBox() {
		this.categoryComboBox.setItems(
				FXCollections.observableArrayList(ItemCategory.values())
		);
		// View → ViewModel
		this.categoryComboBox.getSelectionModel()
				.selectedItemProperty()
				.addListener((obs, oldV, newV) ->
				this.viewModel.categoryProperty().set(newV)
				);

		// ViewModel → View
		this.viewModel.categoryProperty()
				.addListener((obs, oldV, newV) -> {
					if (newV == null) {
						this.categoryComboBox.getSelectionModel().clearSelection();
					} else {
						this.categoryComboBox.getSelectionModel().select(newV);
					}
				});
	}

	private void bindErrorLabel() {

		StringBinding computedError = Bindings.createStringBinding(() -> {
			String vmErr = this.viewModel.errorMessageProperty().get();
			if (vmErr != null && !vmErr.trim().isEmpty()) {
				return vmErr;
			}
			String name = this.viewModel.itemNameProperty().get();
			String unit = this.viewModel.unitProperty().get();
			ItemCategory cat = this.viewModel.categoryProperty().get();
			int qty = this.viewModel.quantityProperty().get();
			boolean validName = name != null && !name.trim().isEmpty();
			boolean validUnit = unit != null && !unit.trim().isEmpty();
			boolean validCat = cat != null;
			if (validName && validUnit && validCat && qty <= 0) {
				return "Quantity must be a positive integer.";
			}
			return "";
		},

				this.viewModel.errorMessageProperty(),
				this.viewModel.itemNameProperty(),
				this.viewModel.unitProperty(),
				this.viewModel.categoryProperty(),
				this.viewModel.quantityProperty());
		this.errorLabel.textProperty().bind(computedError);

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

	private void bindSuccessLabel() {
		this.successLabel.textProperty()
				.bind(this.viewModel.successMessageProperty());
	}

	private void bindAddButton() {
		BooleanBinding allFieldsValid = Bindings.createBooleanBinding(() -> {
			String name = this.viewModel.itemNameProperty().get();
			String unit = this.viewModel.unitProperty().get();
			ItemCategory cat = this.viewModel.categoryProperty().get();
			int qty = this.viewModel.quantityProperty().get();
			return name != null && !name.trim().isEmpty()
					&& unit != null && !unit.trim().isEmpty()
					&& qty > 0
					&& cat != null;
		},
				this.viewModel.itemNameProperty(),
				this.viewModel.unitProperty(),
				this.viewModel.categoryProperty(),
				this.viewModel.quantityProperty());
		this.addButton.disableProperty().bind(allFieldsValid.not());
	}

	private void bindButtonActions() {
		this.addButton.setOnAction(evt -> this.handleAdd());
		this.backButton.setOnAction(evt -> NavigationManager.goBack());
	}

	/**
	 * Sets the performing user.
	 *
	 * @param user the new performing user
	 */
	public void setPerformingUser(User user) {
		this.performingUser = user;
	}

	@FXML
	private void handleBack(ActionEvent event) {
		NavigationManager.goBack();
	}
}