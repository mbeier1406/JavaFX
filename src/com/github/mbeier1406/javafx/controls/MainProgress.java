package com.github.mbeier1406.javafx.controls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Zeigt die Nutzung von {@linkplain ProgressBar} und {@linkplain ProgressIndicator}.
 * @author mbeier
 */
public class MainProgress extends Application {

	private CheckTask checkTask;

	private final Label labelInfo = new Label("Kopiere Dateien...");
	private final Label labelStatus = new Label();

	private final Button buttonStart = new Button("Start");
	private final Button buttonAbbruch = new Button("Abbruch");

	private final ProgressBar progressBar = new ProgressBar();
	private final ProgressIndicator progressIndicator = new ProgressIndicator();

	private final ListView<String> listView = new ListView<>();

	/**
	 * Setzt die Schalflächen für das Starten/Stoppen und die Anzeige auf den Anfangszustand zurück.
	 */
	private void reset() {
		buttonStart.setDisable(false);
		buttonAbbruch.setDisable(true);
		progressBar.progressProperty().unbind();
		progressBar.setProgress(0);
		progressIndicator.progressProperty().unbind();
		progressIndicator.setProgress(0);
		labelStatus.textProperty().unbind();
		labelStatus.setText("");

	}

	/**
	 * Startet die Anwendung.
	 * @param stage die Main-Stage
	 */
	@Override
	public void start(Stage stage) throws Exception {

		/* Grundzusand der Schaltfläche und der Dateianzeige */
		reset();
		listView.setPrefSize(700, 400);
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					if ( listView.getItems().size() == 0 )
						return; // Keine Dateien in der Anzeige
					// Desktop.getDesktop().open(new File(listView.getSelectionModel().getSelectedItem()));
					Runtime.getRuntime().exec("/usr/bin/gedit "+listView.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		/* Schaltfläche zum Starten des Tasks einstellen */
		buttonStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				checkTask = new CheckTask();
				checkTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {
					@Override
					public void handle(WorkerStateEvent event) {
						reset();
						listView.setItems(FXCollections.observableArrayList(
								checkTask
									.getValue()
									.stream()
									.map(File::toString)
									.collect(Collectors.toList())
								));
					}
				});

				buttonStart.setDisable(true);
				buttonAbbruch.setDisable(false);
				progressBar.setProgress(0);
				progressBar.progressProperty().unbind();
				progressBar.progressProperty().bind(checkTask.progressProperty());
				progressIndicator.setProgress(0);
				progressIndicator.progressProperty().unbind();
				progressIndicator.progressProperty().bind(checkTask.progressProperty());
				labelStatus.textProperty().unbind();
				labelStatus.textProperty().bind(checkTask.messageProperty());
				System.out.println("Start...");
				new Thread(checkTask).start();
			}
		});

		/* Schaltfläche zum Stoppen des Tasks einstellen */
		buttonAbbruch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				checkTask.cancel();
				reset();
				listView.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
			}
		});

		/* Oberfläche zusammensetzen */
		final var flowPane = new FlowPane();
		flowPane.setVgap(20);
		flowPane.setHgap(20);
		flowPane.setPadding(new Insets(20));
		flowPane.getChildren().addAll(labelInfo, progressBar, progressIndicator, labelStatus,buttonStart, buttonAbbruch, listView);
		final var scene = new Scene(flowPane, 800, 500);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Diese Klasse repräsentiert den Task, der ausgeführt werden soll.
	 * In diesem Fall werden lediglich die Dateien eines Verzeichnisses ermittelt.
	 * Es werden {@linkplain Task#getProgress()} und {@linkplain Task#getMessage()}
	 * zur Anzeige in der Anwendung gesetzt.
	 */
	public class CheckTask extends Task<List<File>> {

		@Override
		protected List<File> call() throws Exception {
			final List<File> filesChecked = new ArrayList<>();
			final var files = new File("/home/mbeier").listFiles();
			int i = 0;
			for ( File f : files ) {
				System.out.println("Datei: "+f.getName());
				checkFile(f);
				filesChecked.add(f);
				i++;
				this.updateProgress(i, files.length);
			}
			return filesChecked;
		}

		/** An dieser Stelle die jeweilige Datei verarbeiten/prüfen */
		private void checkFile(File f) throws InterruptedException {
			this.updateMessage("Prüfe "+f.getName()+"...");
			Thread.sleep(100);
		}

	}

}
