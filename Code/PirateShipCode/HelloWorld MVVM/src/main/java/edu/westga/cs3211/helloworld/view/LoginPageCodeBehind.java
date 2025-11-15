package edu.westga.cs3211.helloworld.view;

import edu.westga.cs3211.helloworld.viewmodel.LoginPageViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

	    boolean success = this.viewModel.authenticateUser();
	    if (success) {
	        this.greetingLabel.setText("Login Successful!");
	    } else {
	        this.greetingLabel.setText("Invalid username or password.");
	    }

	}
}
