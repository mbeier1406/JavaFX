package com.github.mbeier1406.javafx.controls;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Liest Dateien aus einem ausgewählten Ordner und bearbeitet sie  (z. B. benennt sie um usw.).
 * Kombiniert verschiedene Controls wie {@linkplain FileChooser}, {@linkplain TextField}, {@linkplain Label} etc.
 * @author mbeier
 */
public class MainDateienBearbeiten extends Application {

	/** Zeigt die ausgewählten Dateien an */
	private ListView<String> listView = new ListView<>();

	/** {@inheritDoc} */
	@Override
	public void start(Stage stage) throws Exception {

		// Menü
		final var menueFilesItemOpen = new MenuItem("Öffnen");
		menueFilesItemOpen.setOnAction(event -> {
			final var fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
			listView.setItems(FXCollections.observableArrayList(
				Optional
					.ofNullable(fileChooser.showOpenMultipleDialog(stage))
					.orElse(Collections.emptyList())
					.stream()
					.map(File::getAbsolutePath)
					.collect(toList())));
		});

		final var menueFilesItemExit = new MenuItem("Ende");
		menueFilesItemExit.setOnAction(event -> { Platform.exit(); });

		final var menueFiles = new Menu("Dateien");
		menueFiles.getItems().addAll(menueFilesItemOpen, new SeparatorMenuItem(),menueFilesItemExit);
		final var menuBar = new MenuBar();
		menuBar.getMenus().add(menueFiles);

		// VBox für die Steuerungselemente
		final var labelUmbenennen = new Label("Neuer Dateiname:");
		final var textFieldUmbenennen = new TextField();
		final var buttonUmbenennen = new Button("Umbenennen");
		buttonUmbenennen.setOnAction(event -> {
			if ( textFieldUmbenennen.getText() != null && !textFieldUmbenennen.getText().isEmpty() )
				if ( listView.getItems().size() > 0 ) {
					ObservableList<String> items = listView.getItems();
					System.out.println("Dateien: "+items);
					final var dateienNeu = new ArrayList<String>();
					items.stream().forEach(datei -> {
						dateienNeu.add(datei.replaceAll("^(.*)\\.txt", "$1_"+textFieldUmbenennen.getText()+".txt"));
						new File(datei).renameTo(new File(dateienNeu.get(dateienNeu.size()-1)));
					});
					items.clear();
					items.addAll(dateienNeu);
					System.out.println("Dateien (neu): "+items);
					listView.refresh();
				}
		});

		final var labelNeu = new Label("Neue Datei:");
		final var textFieldNeu = new TextField();
		final var buttonNeu = new Button("Anlegen");

		final var separator = new Separator();
		separator.setPadding(new Insets(10, 0, 5 , 0));

		final var vBox = new VBox(5);
		vBox.getChildren().addAll(labelUmbenennen, textFieldUmbenennen, buttonUmbenennen, separator, labelNeu, textFieldNeu, buttonNeu);
		vBox.setPadding(new Insets(5, 10, 5 , 10));
		vBox.setAlignment(Pos.CENTER_LEFT);

		// Zusammenbauen
		final var borderPane = new BorderPane();
		borderPane.setTop(menuBar);
		borderPane.setLeft(vBox);
		borderPane.setCenter(listView);
		BorderPane.setMargin(listView, new Insets(5, 5, 5, 0));
	
		final var scene = new Scene(borderPane, 400, 400);
		stage.setScene(scene);
		stage.setTitle("Dateien bearbeiten");
		stage.show();

	}

	public static final void main(String[] args) {
		launch(args);
	}

}
