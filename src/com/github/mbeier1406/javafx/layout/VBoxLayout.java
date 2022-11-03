package com.github.mbeier1406.javafx.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten des Syles und Layouts für die {@linkplain VBox}.
 * @author mbeier
 */
public class VBoxLayout extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final var vbox = new VBox();
		vbox.setPadding(new Insets(10, 20, 10, 20));
		vbox.setSpacing(15);
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html
		// https://www.farb-tabelle.de/de/farbtabelle.htm
		vbox.setStyle("-fx-background-color: #F0FFFF;");
		List<Node> inhalt = new ArrayList<Node>(List.of(new Text("Links")));
		inhalt.addAll(Arrays.asList(new Hyperlink[] { new Hyperlink("Verkauf"), new Hyperlink("Marketing") }));
		vbox.getChildren().addAll(inhalt);
		final var scene = new Scene(vbox, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
