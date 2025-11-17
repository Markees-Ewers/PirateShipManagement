package edu.westga.cs3211.pirateship;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import edu.westga.cs3211.pirateship.view.NavigationManager;

/**
 * Main Application class.
 * CS 3211
 *  @version fall 2025
 *  @author me00070
 */
public class Main extends Application {

	private static final String WINDOW_TITLE = "Pirate Ship Inventory Management";
	private static final String GUI_FXML = "view/LoginPage.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {
			NavigationManager.init(primaryStage);
			// load initial GUI using NavigationManager so the back stack is managed
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(GUI_FXML));
			Pane root = (Pane) loader.load();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.setTitle(WINDOW_TITLE);
			primaryStage.show();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
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