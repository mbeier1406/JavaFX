package com.github.mbeier1406.javafx.graph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		final Parent root = new FXMLLoader().load(getClass().getResourceAsStream("Application.fxml"));
		final var borderPane = new BorderPane();

		primaryStage.setTitle("Graphen");
		primaryStage.setScene(new Scene(borderPane, 100, 100));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(Main.class, args);
	}

}
