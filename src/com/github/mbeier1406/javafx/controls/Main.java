package com.github.mbeier1406.javafx.controls;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
		gridPane.add(getButtonExample(), 1, 0);
		final var scene = new Scene(gridPane, 800, 400);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Demonstriert die Verwendung von Labeln:
	 * <ol>
	 * <li>Mit Text und Farbe</li>
	 * <li>Mit Text und Bild</li>
	 * <li>Nur mit Bild und Skalierungseffekt</li>
	 * </ol>
	 * @return das Layout mit den Labeln
	 * @see #start(Stage)
	 */
	private Pane getLabelExample() {
		final var vBox = new VBox(5); // vBox.setSpacing(5);
		final var label1 = new Label("Demonstriert die Nutzung von Farbe und Fonts");
		label1.setTextFill(Color.web("#00f0a3"));
		label1.setFont(new Font("Helvetica", 20));
		final var label2 = new Label("GitHub", new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		final var label3 = new Label();
		label3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		label3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				label3.setScaleX(1.5); label3.setScaleY(1.5);
				label3.setTranslateX(20); label3.setTranslateY(20);
			}
		});
		label3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				label3.setScaleX(1); label3.setScaleY(1);
				label3.setTranslateX(0); label3.setTranslateY(0);
			}
		});
		vBox.getChildren().addAll(label1, label2, label3);
		return vBox;
	}

	private Pane getButtonExample() {
		final var vBox = new VBox(5); // vBox.setSpacing(5);
		final var button1 = new Button("Verschieb mich...", new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		button1.setFont(new Font("Helvetica", 15));
		button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				button1.setTranslateX(button1.getTranslateX() > 0 ? 0 : 50); 
			}
		});
		final var label = new Label();
		final var toggleGroup = new ToggleGroup();
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
				if ( toggleGroup.getSelectedToggle() != null )
					label.setText((String) toggleGroup.getSelectedToggle().getUserData());
			}
		});
		final var rb1 = new RadioButton("Auswahl 1");
		final var rb2 = new RadioButton("Auswahl 2");
		final var rb3 = new RadioButton("Auswahl 3");
		rb3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		rb3.setSelected(true);
		rb1.setToggleGroup(toggleGroup);
		rb2.setToggleGroup(toggleGroup);
		rb3.setToggleGroup(toggleGroup);
		rb1.setUserData("Erster");
		rb2.setUserData("Zweiter");
		rb3.setUserData("Dritter");
		final var rbVBox = new VBox(3);
		rbVBox.getChildren().addAll(rb1, rb2, rb3);
	
		vBox.getChildren().addAll(button1, rbVBox, label);
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
