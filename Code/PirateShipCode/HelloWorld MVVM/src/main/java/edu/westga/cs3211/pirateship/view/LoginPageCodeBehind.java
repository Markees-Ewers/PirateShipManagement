package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.LoginPageViewModel;
import edu.westga.cs3211pirateship.viewmodel.LoginResult;
import edu.westga.cs3211pirateship.viewmodel.MainMenuViewModel;
import edu.westga.cs3211.pirateship.model.User;
import edu.westga.cs3211.pirateship.model.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * The Class CodeBehind.
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class LoginPageCodeBehind {
	@FXML
	private Label greetingLabel;
	@FXML
	private TextField passwordTextField;
	@FXML
	private Button submitButton;
	@FXML
	private TextField userNameTextField;

	private LoginPageViewModel viewModel;

	/**
	 * Instantiates a new greeting code behind.
	 * 
	 * @precondition none
	 * @precondition none
	 */
	public LoginPageCodeBehind() {
		this.viewModel = new LoginPageViewModel();
	}

	@FXML
	void initialize() {
		this.bindComponentsToViewModel();
	}

	private void bindComponentsToViewModel() {
		this.userNameTextField.textProperty().bindBidirectional(this.viewModel.userNameProperty());
		this.passwordTextField.textProperty().bindBidirectional(this.viewModel.passwordProperty());
	}

	@FXML

	void handleSubmit() {

		LoginResult result = viewModel.authenticateUser();
		this.greetingLabel.setTextFill(Color.BLACK);
		switch (result) {
		case SUCCESS_CREWMATE:
			this.greetingLabel.setText("Welcome, Crew Member!");
			// navigate to main menu and configure for crewmate
			this.navigateToMainMenu(UserRole.CREWMATE);
			break;
		case SUCCESS_QUARTERMASTER:
			this.greetingLabel.setText("Welcome, Quartermaster!");
			// navigate to main menu and configure for quartermaster
			this.navigateToMainMenu(UserRole.QUARTERMASTER);
			break;
		case FAILURE:
		default:
			this.greetingLabel.setText("Invalid username or password.");
			this.greetingLabel.setTextFill(Color.RED);
		}
	}

	private void navigateToMainMenu(UserRole role) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
			Parent root = loader.load();

			// create and configure view model for the main menu and pass it to controller
			MainMenuViewModel menuViewModel = new MainMenuViewModel();
			menuViewModel.configureForRole(role);

			// pass performing user to the main menu viewmodel
			User user = this.viewModel.getAuthenticatedUser();
			if (user != null) {
				menuViewModel.setPerformingUser(user);
			}

			MainMenuCodeBehind controller = loader.getController();
			if (controller != null) {
				controller.setViewModel(menuViewModel);
			}

			Stage stage = (Stage) this.submitButton.getScene().getWindow();
			stage.setScene(new Scene(root));
		} catch (IOException exc) {
			exc.printStackTrace();
			this.greetingLabel.setText("Failed to load main menu.");
			this.greetingLabel.setTextFill(Color.RED);
		}
	}
}