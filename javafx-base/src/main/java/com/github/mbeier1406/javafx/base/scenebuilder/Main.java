package com.github.mbeier1406.javafx.base.scenebuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Zeigt die Einbindung von {@code Application.fxml}, die über den SceneBuilder erstellt wurde.
 * @author mbeier
 */
public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
		final var scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public static final void main(String[] args) {
		launch(args);
	}
}
