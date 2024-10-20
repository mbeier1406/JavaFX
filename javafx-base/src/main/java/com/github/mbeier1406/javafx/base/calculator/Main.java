package com.github.mbeier1406.javafx.base.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Startet den Taschenrechner.
 * @author mbeier
 */
public class Main extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		final Parent root = FXMLLoader.load(getClass().getResource("Controller.fxml"));
		final var scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Calculator");
		stage.show();
	}

	public static final void main(String[] args) {
		launch(args);
	}
}
