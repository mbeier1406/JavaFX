package com.github.mbeier1406.javafx.layout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.MotionBlur;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten von Effekten auf Gruppen {@linkplain Group}.
 * @author mbeier
 */
public class GroupDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final var group = new Group();
		final var label = new Label("Label");
		final var link = new Hyperlink("Link");
		link.setTranslateX(50);
		final var button = new Button("Button");
		button.setTranslateX(100);
		final var text = new Label("Text");
		text.setTranslateX(200);
		group.getChildren().addAll(label, link, button, text);
		group.setEffect(new MotionBlur());
		group.setTranslateY(100);
		final var scene = new Scene(group, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
