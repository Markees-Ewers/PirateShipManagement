package edu.westga.cs3211.pirateship;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Main Application class.
 * CS 3211
 *  Fall 2025
 */
public class Main extends Application {

	private static final String WINDOW_TITLE = "Pirate Ship Inventory Management";
	private static final String GUI_FXML = "view/LoginPage.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = this.loadGui();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setTitle(WINDOW_TITLE);
			primaryStage.show();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private Pane loadGui() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(GUI_FXML));
		return (Pane) loader.load();
	}

	/**
	 * Entry point for the application
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
