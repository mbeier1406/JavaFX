package com.github.mbeier1406.javafx.base.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten des Styles und Layouts für die {@linkplain FlowPane}.
 * @author mbeier
 */
public class FlowPaneLayout extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final Rectangle r1 = new Rectangle(200, 200, Color.BLACK),
				r2 = new Rectangle(150, 150, Color.RED),
				r3 = new Rectangle(100, 100, Color.BLUE),
				r4 = new Rectangle(50, 50, Color.GREEN);
		final var flowPane = new FlowPane();
		flowPane.getChildren().addAll(r1, r2, r3, r4);
		flowPane.setVgap(10.0);
		flowPane.setHgap(10.0);
		final var scene = new Scene(flowPane, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
