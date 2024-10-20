package com.github.mbeier1406.javafx.base.db;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogin extends Application {

	/** Das Basisverzeichnis für die FXML-Dateien ist {@value} */
	public static final String FXML_BASE_DIR = "/com/github/mbeier1406/javafx/db/ui/";

	@Override
	public void start(Stage primaryStage) throws Exception {
		final var scene = new Scene(FXMLLoader.load(getClass().getResource(FXML_BASE_DIR+"MainLogin.fxml")));
		scene.getStylesheets().add(getClass().getResource("ui/mainlogin.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
