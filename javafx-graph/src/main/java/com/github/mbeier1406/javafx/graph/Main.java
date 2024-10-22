package com.github.mbeier1406.javafx.graph;

import java.awt.Desktop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Startet die ANwendung zur Anzeige von Graphen.
 */
public class Main extends Application {

	public static final Logger LOGGER = LogManager.getLogger(Main.class);

	/** Für das Öffnen von Dateien */
	@SuppressWarnings("unused")
	private Desktop desktop = Desktop.getDesktop();

	/** Bildschirmdefinition */
	private Screen screen = Screen.getPrimary();

	/** Öffnet das Fenster und zeichnet ein Standard-Koordinatensystem */
	@Override
	public void start(Stage primaryStage) throws Exception {

		final var fxmlLoader = new FXMLLoader();
		final Parent root = fxmlLoader.load(getClass().getResourceAsStream("Application.fxml"));
		final var controller = (Controller) fxmlLoader.getController();
		LOGGER.info("Screen {}: Starte controller {}", screen, controller);
		final var scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

		primaryStage.setTitle("Graphen");
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.setX(0);
		primaryStage.setY(0);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(Main.class, args);
	}

}
