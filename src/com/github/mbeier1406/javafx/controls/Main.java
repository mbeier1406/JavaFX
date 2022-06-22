package com.github.mbeier1406.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Demonstriert die Verwendung von UI Controls.
 * <p>
 * Ein einfaches Beispiel in dem die Nutzung einiger Controls
 * wie Labels, Buttons usw. gezeigt wird.
 * </p>
 * @author mbeier
 */
public class Main extends Application {

	/** Das im Programm verwendete Image ist {@value} */
	private static final String IMAGES_GITHUB_PNG = "../Images/github.png";

	/**
	 * {@inheritDoc}
	 * <p>Erzeugt eine Anwendung, die diverse Controls in einem Fenster darstellt.</p>
	 */
	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("Demonstration Controls");
		final var gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(10);
		gridPane.add(getLabelExample(), 0, 0);
		final var scene = new Scene(gridPane, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Demonstriert die Verwendung von Labeln:
	 * <ol>
	 * <li>Mit Text und Farbe</li>
	 * <li>Mit Text und Bild</li>
	 * <li>Nur mit Bild</li>
	 * </ol>
	 * @return das Layout mit den Labeln
	 * @see #start(Stage)
	 */
	private Pane getLabelExample() {
		final var vBox = new VBox();
		vBox.setSpacing(5);
		final var label1 = new Label("Demonstriert die Nutzung von Farbe");
		label1.setTextFill(Color.web("#00f0a3"));
		final var label2 = new Label("GitHub", new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		label2.setFont(new Font("Helvetica", 15));
		final var label3 = new Label();
		label3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		vBox.getChildren().addAll(label1, label2, label3);
		return vBox;
	}

	/**
	 * Startet die Anwendung.
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
