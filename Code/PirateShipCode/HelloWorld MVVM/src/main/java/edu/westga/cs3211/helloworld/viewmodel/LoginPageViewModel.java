package edu.westga.cs3211.helloworld.viewmodel;

import edu.westga.cs3211.helloworld.model.LoginAuthenticator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class ViewModel.
 * 
 * @author CS 3211
 * @version Fall 2025
 */


public class LoginPageViewModel {

    private StringProperty userNameProperty;
    private StringProperty passwordProperty;
    private LoginAuthenticator authenticator;

    public LoginPageViewModel() {
        this.userNameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
        this.authenticator = new LoginAuthenticator();
    }
    public StringProperty userNameProperty() {
        return this.userNameProperty;
    }

    public StringProperty passwordProperty() {
        return this.passwordProperty;
    }


    // 🚀 This is where the actual login check happens

    public boolean authenticateUser() {
        String username = this.userNameProperty.get();
        String password = this.passwordProperty.get();
        return this.authenticator.authenticate(username, password);
    }

}
