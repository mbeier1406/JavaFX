package com.github.mbeier1406.javafx.db;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLogin extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final var scene = new Scene(FXMLLoader.load(getClass().getResource("MainLogin.fxml")));
		scene.getStylesheets().add(getClass().getResource("mainlogin.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
