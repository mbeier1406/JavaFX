package com.github.mbeier1406.javafx.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Startet die ANwendung zur Anzeige von Graphen.
 */
public class Main extends Application {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) throws Exception {

		final var fxmlLoader = new FXMLLoader();
		final Parent root = fxmlLoader.load(getClass().getResourceAsStream("Application.fxml"));
		final var controller = (Controller) fxmlLoader.getController();
		LOGGER.info("Starte controller {}", controller);
		final var scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

		primaryStage.setTitle("Graphen");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(Main.class, args);
	}

}
