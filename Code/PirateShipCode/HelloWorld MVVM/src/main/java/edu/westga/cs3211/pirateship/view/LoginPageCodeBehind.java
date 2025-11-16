package edu.westga.cs3211.pirateship.view;

import edu.westga.cs3211pirateship.viewmodel.LoginPageViewModel;
import edu.westga.cs3211pirateship.viewmodel.LoginResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
			break;
		case SUCCESS_QUARTERMASTER:
			this.greetingLabel.setText("Welcome, Quartermaster!");
			break;
		case FAILURE:
		default:
			this.greetingLabel.setText("Invalid username or password.");
			this.greetingLabel.setTextFill(Color.RED);
		}
	}
}
