package com.github.mbeier1406.javafx.base.controls;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Zeigt die Verwendung eines {@linkplain FileChooser}s.
 * @author mbeier
 */
public class MainFileChooser extends Application {

	/** Zum Öffnen ausgewählter Dateien */
	private Desktop desktop = Desktop.getDesktop();

	@Override
	public void start(Stage stage) throws Exception {

		final var flowPane = new FlowPane();

		final var buttonEineDatei = new Button("Datei auswählen");
		buttonEineDatei.setOnAction(event -> {
			final var fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("PNG", "*.png"),
					new FileChooser.ExtensionFilter("JPG", "*.jpg"));
			final var file = fileChooser.showOpenDialog(stage);
			if ( file == null ) return;
			System.out.println("Datei: "+file.getAbsolutePath());
			// openFile(file);
			ImageView imageView = loadImage(file);
			flowPane.getChildren().add(imageView);
			processImage(imageView);
		});

		final var buttonMehrereDateien = new Button("Datei(en) auswählen");
		buttonMehrereDateien.setOnAction(event -> {
			final var fileChooser = new FileChooser();
			List<File> files = fileChooser.showOpenMultipleDialog(stage);
			if ( files == null ) return;
			System.out.println("Datei(en): "+files);
		});

		flowPane.setHgap(10);
		flowPane.setPadding(new Insets(5));
		flowPane.getChildren().addAll(buttonEineDatei, buttonMehrereDateien);

		final var scene = new Scene(flowPane, 800, 500);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Öffent die ausgewählte Datei mit dem im OS vorgegebenen Programm.
	 * @param file die ausgewählte Datei
	 */
	@SuppressWarnings("unused")
	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Zeigt die ausgewählte Datei (Image PNG/JPEG) an.
	 * @param file die ausgewählte Image-Datei
	 * @return ein {@linkplain ImageView} aus der Eingabedatei
	 */
	private ImageView loadImage(File file) {
		final var imageView = new ImageView(new Image(file.toURI().toString()));
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);
		return imageView;
	}

	/**
	 * Stellt ein Kontextmenü bereit, das zum Speichern oder Kopieren eines ausgewählten
	 * Images dient.
	 * @param imageView das ausgewählte Image
	 */
	public void processImage(ImageView imageView) {
		final var secondStage = new Stage();
		final var contextMenue = new ContextMenu();
		final var item1 = new MenuItem("Speichern");
		item1.setOnAction(event -> {
			final var fileChooser = new FileChooser();
			final var file = fileChooser.showSaveDialog(secondStage);
			if ( file != null ) {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		final var item2 = new MenuItem("Kopieren", new ImageView(new Image(getClass().getResourceAsStream("../Images/bild0.jpg"))));
		contextMenue.getItems().addAll(item1, item2);
		imageView.setOnContextMenuRequested(event -> {
			contextMenue.show(imageView, event.getScreenX(), event.getScreenY());
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
