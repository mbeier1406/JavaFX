package com.github.mbeier1406.javafx.base.layout;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten von unmanaged Nodes.
 * Bei sich überlappenden Nodes kann das untere für die Maus aktiviert werden.
 * @author mbeier
 */
public class UnmanagedLayout extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final var hbox = new HBox();
		hbox.setPadding(new Insets(10, 20, 10, 20));
		hbox.setSpacing(15);
		final var unmanagedButton = new Button("frei");
		unmanagedButton.setManaged(false);
		unmanagedButton.resizeRelocate(200, 200, 150, 50);
		final var unmanagedText = new Text("Hinweis");
		unmanagedText.setFont(new Font("Arial", 60));
		unmanagedText.setManaged(false);
		unmanagedText.setLayoutX(200);
		unmanagedText.setLayoutY(250);
		unmanagedText.setMouseTransparent(true);
		@SuppressWarnings("serial")
		final Set<Button> buttons = new HashSet<Button>() {{ add(new Button("Eins")); add(new Button("zwei")); ; add(unmanagedButton); }};
		buttons.forEach(b -> b.setPrefSize(100, 25));
		hbox.getChildren().addAll(buttons);
		hbox.getChildren().add(unmanagedText);
		final var scene = new Scene(hbox, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
