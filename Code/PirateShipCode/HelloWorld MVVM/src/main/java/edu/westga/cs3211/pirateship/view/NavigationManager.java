package edu.westga.cs3211.pirateship.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

// TODO: Auto-generated Javadoc
/**
 * Simple navigation manager that keeps a stack of scenes and the primary stage.
 * Controllers should call NavigationManager.navigateTo(loader) to push the
 * current scene and show the loaded view. Calling goBack() will pop the
 * previous scene and show it. If no previous scene exists, the app will exit.
 * 
 * @author Markees Ewers
 * @version Fall 2025
 */
public class NavigationManager {
	/**
	 * If non-null, navigateTo will delegate to this for tests. Set to null to
	 * restore normal behavior.
	 */
	private static volatile TestNavigationDelegate testDelegate = null;
	/** The primary stage. */
	private static Stage primaryStage;
	
	/** The Constant sceneStack. */
	private static final Deque<Scene> SCENESTACK = new ArrayDeque<>();

	/**
	 * Inits the.
	 *
	 * @param stage the stage
	 */
	public static void init(Stage stage) {
		primaryStage = stage;
	}

	/**
	 * Navigate to.
	 *
	 * @param fxmlPath the fxml path
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void navigateTo(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxmlPath));
		navigateTo(loader);
	}

	/**
	 * Load the FXML via the provided FXMLLoader, push the current scene (if any)
	 * onto the stack, set the new scene and return the controller.
	 *
	 * @param loader the loader
	 * @return the object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Object navigateTo(FXMLLoader loader) throws IOException {
		// test hook: if a test delegate is set, let it handle navigation (avoids
		// touching the real primaryStage)
		if (testDelegate != null) {
			return testDelegate.navigate(loader);
		}
		Parent root = loader.load();
		Scene newScene = new Scene(root);
		if (primaryStage == null) {
			throw new IllegalStateException("NavigationManager not initialized with primary stage");
		}
		Scene current = primaryStage.getScene();
		if (current != null) {
			SCENESTACK.push(current);
		}
		primaryStage.setScene(newScene);
		return loader.getController();
	}

	/**
	 * Replace the current scene with the loader's scene without pushing the current
	 * scene onto the stack.
	 *
	 * @param loader the loader
	 * @return the object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Object navigateReplace(FXMLLoader loader) throws IOException {
		Parent root = loader.load();
		Scene newScene = new Scene(root);
		if (primaryStage == null) {
			throw new IllegalStateException("NavigationManager not initialized with primary stage");
		}
		primaryStage.setScene(newScene);
		return loader.getController();
	}

	/**
	 * Navigate replace.
	 *
	 * @param fxmlPath the fxml path
	 * @return the object
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Object navigateReplace(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(fxmlPath));
		return navigateReplace(loader);
	}

	/**
	 * Returns true when there is a previous scene on the stack.
	 * 
	 * @return true, if can go back to previous scene
	 */
	public static boolean canGoBack() {
		return !SCENESTACK.isEmpty();
	}

	/** Go back to the previous scene or exit if none. */
	public static void goBack() {
		if (primaryStage == null) {
			return;
		}
		if (SCENESTACK.isEmpty()) {
			primaryStage.close();
			return;
		}
		Scene previous = SCENESTACK.pop();
		primaryStage.setScene(previous);

		// If returning to the login screen, clear fields so user sees blank inputs
		try {
			Parent root = previous.getRoot();
			// clear username and password fields if present
			Object userNode = root.lookup("#userNameTextField");
			if (userNode instanceof TextField) {
				((TextField) userNode).clear();
			}
			Object passNode = root.lookup("#passwordTextField");
			if (passNode instanceof TextField) {
				((TextField) passNode).clear();
			}
			// clear any greetingLabel
			Object greetNode = root.lookup("#greetingLabel");
			if (greetNode instanceof Label) {
				((Label) greetNode).setText("");
			}
		} catch (Exception ex) {
			// ignore lookup errors; not all scenes have these nodes
		}
	}

	/**
	 * Exits app.
	 */
	public static void exitApp() {
		if (primaryStage != null) {
			primaryStage.close();
		}
	}

	/**
	 * Test hook interface for intercepting navigation during unit tests.
	 */
	public interface TestNavigationDelegate {

		/**
		 * Navigate.
		 *
		 * @param loader the loader
		 * @return the object
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		Object navigate(FXMLLoader loader) throws IOException;
	}
	
}