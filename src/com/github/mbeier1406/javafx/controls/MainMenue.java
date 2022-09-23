package com.github.mbeier1406.javafx.controls;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Demonstriert die Nutzung von {@linkplain MenuBar}, {@linkplain Menu}
 * und {@linkplain MenuItem}.
 */
public class MainMenue extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final var flowPane = new FlowPane();
		flowPane.setHgap(20);
		flowPane.setPadding(new Insets(30));

		final var menuBar = new MenuBar();
		final var manueDatei = new Menu("Datei");
		final var manueDateiItem1 = new MenuItem("Öffnen");
		final var manueDateiItem2 = new MenuItem("Speichern");
		final var manueDateiItem3 = new MenuItem("Speichern unter..");
		final var manueDateiItem4 = new MenuItem("Laden...");
		final var manueDateiItem5 = new MenuItem("Ende");
		manueDateiItem5.setOnAction(event -> { Platform.exit(); });
		manueDatei.getItems().addAll(manueDateiItem1, manueDateiItem2, new SeparatorMenuItem(), manueDateiItem3, manueDateiItem4, manueDateiItem5);

		final var manueBearbeiten = new Menu("Bearbeiten");
		final var manueBearbeitenItem1 = new MenuItem("Zeichnen");
		final var manueBearbeitenItem2 = new MenuItem("Kopieren");
		final var manueBearbeitenItem3 = new MenuItem("Einfügen");
		manueBearbeiten.getItems().addAll(manueBearbeitenItem1, new SeparatorMenuItem(), manueBearbeitenItem2, manueBearbeitenItem3);

		menuBar.getMenus().addAll(manueDatei, manueBearbeiten);

		flowPane.getChildren().add(menuBar);
		final var scene = new Scene(flowPane, 500, 400);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String args) {
		launch(args);
	}

}
