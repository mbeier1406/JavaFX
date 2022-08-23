package com.github.mbeier1406.javafx.dock;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Beispiel für die Erstellung eines Docks mit Programmicons für den Schnellstart.
 * Das Beenden des Docks erfolgt durch einen Doppelklick auf das Dock.
 * @author mbeier
 */
public class Main extends Application {

	/** Verzeichnis mit den Bildern ist {@value} */
	private static final String IMAGES = "../Images/";

	/**
	 * Liefert zu einem Bild einen {@linkplain ImageView} zur Anzeige in der Anwendung.
	 * Die zugehörigen Dateien werden im Verzeichnis {@linkplain #IMAGES} gesucht.
	 * @param datei Kurzname der Datei
	 * @return die ImageView
	 */
	private ImageView getImageView(String datei) {
		return new ImageView(new Image(getClass().getResourceAsStream(IMAGES+datei)));
	}

	@Override
	public void start(Stage stage) throws Exception {

		final var imageViewDock = getImageView("dock.png");
		imageViewDock.setTranslateX(12);
		imageViewDock.setTranslateY(100);
		imageViewDock.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if ( event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 )
					Platform.exit();
			}
		});

		final var group = new Group();
		group.getChildren().addAll(imageViewDock);

		final var scene = new Scene(group, 500, 200);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
