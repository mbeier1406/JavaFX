package com.github.mbeier1406.javafx.events;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * Demonstriert die Verwendung von Eventhandlern.
 * <p>
 * Ein einfaches Beispiel in dem einige Maus- und Keyboard-Events protokolliert werden.
 * </p>
 * @author mbeier
 */
public class Main extends Application {

	/**
	 * {@inheritDoc}
	 * <p>Erzugt eine Anwendung, die auf einige Events reagiert und diese
	 * auf der Console loggt.</p>
	 */
	@Override
	public void start(final Stage stage) throws Exception {

		final var pane = new Pane();
		pane.getStyleClass().add("pane"); // pane.setStyle("-fx-background-color: black");
		final var triangle = new Polygon();
		var triangleDefinition = new Double[] { 0.0, 0.0, 20.0, 10.0, 10.0, 20.0 };
		triangle.getPoints().addAll(triangleDefinition);
		triangle.setFill(Color.WHEAT);
		pane.getChildren().add(triangle);
		final var scene = new Scene(pane, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

		final var keyboadEvent = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				System.out.println("Key: "+ke);
				if ( ke.getCode().getCode() == 'm' )
					triangle.setLayoutX(10);
			}
		};
		scene.addEventHandler(KeyEvent.ANY, keyboadEvent);

		final var mouseEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				System.out.println("Mouse: "+me);
			}
		};
		scene.addEventHandler(MouseEvent.ANY, mouseEvent);

	}

	/**
	 * Startet die Anwendung.
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
