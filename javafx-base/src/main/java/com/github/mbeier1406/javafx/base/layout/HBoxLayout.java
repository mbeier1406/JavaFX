package com.github.mbeier1406.javafx.base.layout;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten des Styles und Layouts für die {@linkplain HBox}.
 * @author mbeier
 */
public class HBoxLayout extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final var hbox = new HBox();
		hbox.setPadding(new Insets(10, 20, 10, 20));
		hbox.setSpacing(15);
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html
		// https://www.farb-tabelle.de/de/farbtabelle.htm
		hbox.setStyle("-fx-background-color: #336699;");
		@SuppressWarnings("serial")
		final Set<Button> buttons = new HashSet<Button>() {{ add(new Button("Eins")); add(new Button("zwei")); }};
		buttons.forEach(b -> b.setPrefSize(100, 10));
		hbox.getChildren().addAll(buttons);
		final var scene = new Scene(hbox, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
