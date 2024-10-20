package com.github.mbeier1406.javafx.base.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten des Styles und Layouts für die {@linkplain FlowPane}.
 * @author mbeier
 */
public class AnchorPaneLayout extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final var button = new Button("Button");
		final var toolBar = new ToolBar();
		toolBar.getItems().addAll(new Button("Start"), new Button("Ende"));
		final var anchorPane = new AnchorPane();
		anchorPane.getChildren().addAll(button, toolBar);
		AnchorPane.setTopAnchor(button, 10.0);
		AnchorPane.setRightAnchor(button, 10.0);
		AnchorPane.setTopAnchor(toolBar, 10.0);
		AnchorPane.setLeftAnchor(toolBar, 10.0);
		final var scene = new Scene(anchorPane, 400, 400);
		stage.setScene(scene);
		stage.show();

	}

	public static final void main(String[] args) {
		launch(args);
	}

}
