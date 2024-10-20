package com.github.mbeier1406.javafx.dock;

import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Beispiel für die Erstellung eines Docks mit Programmicons für den Schnellstart.
 * Das Beenden des Docks erfolgt durch einen Doppelklick auf das Dock.
 * @author mbeier
 */
public class Main extends Application {

	/** Verzeichnis mit den Bildern ist {@value} */
	private static final String IMAGES = "../Images/";

	/** Die Images und Pfade zu den Programmen im Dock werden in {@value} gespeichert */
	private static final AnwendungsIF[] DOCK_APPS = new AnwendungsIF[] {
			new AnwendungsIF() {
				@Override public String getIcon() { return "eclipse.png"; }
				@Override public String getProgram() { return null; }
			},
			new AnwendungsIF() {
				@Override public String getIcon() { return "firefox.png"; }
				@Override public String getProgram() { return "/bin/sh -c /snap/firefox/1670/usr/lib/firefox/firefox"; }
			},
			new AnwendungsIF() {
				@Override public String getIcon() { return "textedit.png"; }
				@Override public String getProgram() { return "/bin/sh -c /usr/bin/gedit --gapplication-service"; }
			},
			new AnwendungsIF() {
				@Override public String getIcon() { return "mail.png"; }
				@Override public String getProgram() { return null; }
			}
	};

	/** Größe des Bildschirms zur Positionierung des Docks */
	public final Rectangle2D screenSize = Screen.getPrimary().getBounds();

	/**
	 * Liefert zu einem Bild einen {@linkplain ImageView} zur Anzeige in der Anwendung.
	 * Die zugehörigen Dateien werden im Verzeichnis {@linkplain #IMAGES} gesucht.
	 * @param datei Kurzname der Datei
	 * @return die ImageView
	 */
	private ImageView getImageView(String datei) {
		return new ImageView(new Image(getClass().getResourceAsStream(IMAGES+datei)));
	}

	/** {@inheritDoc} */
	@Override
	public void start(Stage stage) throws Exception {

		final var group = new Group();

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
		group.getChildren().add(imageViewDock);

		IntStream.range(0, DOCK_APPS.length).forEach(i -> {
			final var imageView = getImageView(DOCK_APPS[i].getIcon());
			imageView.setTranslateX(100 + 80*i);
			imageView.setTranslateY(100);
			imageView.setScaleX(0.8);
			imageView.setScaleY(0.8);
			imageView.setEffect(new Reflection());
			imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					imageView.setScaleX(1.0);
					imageView.setScaleY(1.0);
				}
			});
			imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					imageView.setScaleX(0.8);
					imageView.setScaleY(0.8);
				}
			});
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					try {
						System.out.println(DOCK_APPS[i].info());
						Runtime.getRuntime().exec(DOCK_APPS[i].getProgram());
					}
					catch ( Exception e ) {
						e.printStackTrace();
					}
				}
			});
			group.getChildren().add(imageView);
		});

		final var scene = new Scene(group, 500, 200);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setX(screenSize.getWidth()/2 - 280);
		stage.setY(screenSize.getHeight() - 150);
		stage.show();

	}

	/**
	 * Definiert alle Funktionen zum Anzeigen und Starten der Anwendungen im Dock.
	 * @author mbeier
	 */
	public interface AnwendungsIF {
		/**
		 * Liefert den Namen der Datei (ohne Pfad), die das Image für den Icon enthölt
		 * @return den Dateinamen
		 */
		public String getIcon();
		/**
		 * Liefert den Pfad zur Anwendung.
		 * @return den Pfad
		 */
		public String getProgram();
		public default String info() {
			return "AnwendungsIF [getIcon()=" + getIcon() + "; getProgram()=" + getProgram() + "]";
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
