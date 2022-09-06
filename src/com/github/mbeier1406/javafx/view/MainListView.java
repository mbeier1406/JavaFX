package com.github.mbeier1406.javafx.view;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Demonstriert die Benutzung von {@linkplain ListView}.
 * @author mbeier
 */
public class MainListView extends Application {

	private ListView<String> listView = new ListView<String>();

	@Override
	public void start(Stage stage) throws Exception {

		final var hBox = new HBox(5);

		// Spalte mit Anzeigen
		final var vBoxLabel = new VBox();
		final var labelAusgewaehlt = new Label("Ausgewählt:");
		labelAusgewaehlt.setFont(new Font(15.0));
		final var labelFokus = new Label("Fokus:");
		labelFokus.setFont(new Font(15.0));
		final var labelEditiert = new Label("Editiert:");
		labelEditiert.setFont(new Font(15.0));
		vBoxLabel.getChildren().addAll(labelAusgewaehlt, labelFokus, labelEditiert);

		// Spalte mit Listview
		final ObservableList<String> observableList = FXCollections.observableArrayList();
		observableList.addAll("Berlin", "Hamburg", "München", "Bremen", "Frankfurt", "Düsseldorf", "Dresden");
		listView.setItems(observableList);
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView.setOrientation(Orientation.VERTICAL);
		listView.setEditable(true);
		listView.setCellFactory(TextFieldListCell.forListView());
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				labelAusgewaehlt.setText("Ausgewählt: "+listView.getSelectionModel().getSelectedItems()+" ("+
						listView.getSelectionModel().getSelectedIndices()+")");
				labelFokus.setText("Fokus: "+listView.getFocusModel().getFocusedItem()+" ("+
						listView.getFocusModel().getFocusedIndex()+")");
			}
		});
		listView.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(ListView.EditEvent<String> event) {
				listView.getItems().set(event.getIndex(), event.getNewValue());
				labelEditiert.setText("Editiert: "+event.getIndex());
			}
		});
		listView.setOnEditStart(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(EditEvent<String> event) {
				labelEditiert.setText("Editieren... "+event.getIndex());
			}
		});
		listView.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>() {
			@Override
			public void handle(EditEvent<String> event) {
				labelEditiert.setText("Editieren beendet: "+event.getIndex());
			}
		});

		// Spalte mit Buttons
		final var vBoxButton = new VBox();
		final var buttonList = Arrays
				.stream(buttons)
				.map(l -> { var b = new Button(l.getLabel()); b.setOnAction(l.getEventHandler()); b.setMaxWidth(Double.MAX_VALUE); return b; })
				.collect(Collectors.toList());
		vBoxButton.getChildren().addAll(buttonList);

		hBox.getChildren().addAll(listView, vBoxButton, vBoxLabel);
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html
		hBox.setStyle("-fx-padding: 10;"
				+ "-fx-border-style: solid inside;"
				+ "-fx-border-width: 4;"
				+ "-fx-border-radius: 5;"
				+ "-fx-border-color: black;");
		final var scene = new Scene(hBox, 600, 400);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/** Die Schnittstelle definiert die notwendigen Eigenschaften der Schaltflächen */
	public interface ButtonIF {
		public String getLabel();
		public EventHandler<ActionEvent> getEventHandler();
	}

	/** Definition der benötigten Schaltflächen */
	public ButtonIF[] buttons = {
		new ButtonIF() {
			@Override public String getLabel() { return "Select All"; }
			@Override public EventHandler<ActionEvent> getEventHandler() {
				return new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						listView.getSelectionModel().selectAll();
					}
				};
			}
		},
		new ButtonIF() {
			@Override public String getLabel() { return "Clear All"; }
			@Override public EventHandler<ActionEvent> getEventHandler() {
				return new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						listView.getSelectionModel().clearSelection();
					}
				};
			}
		},
		new ButtonIF() {
			@Override public String getLabel() { return "Select First"; }
			@Override public EventHandler<ActionEvent> getEventHandler() {
				return new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						listView.getSelectionModel().selectFirst();
					}
				};
			}
		},
		new ButtonIF() {
			@Override public String getLabel() { return "Select Last"; }
			@Override public EventHandler<ActionEvent> getEventHandler() {
				return new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						listView.getSelectionModel().selectLast();
					}
				};
			}
		},
		new ButtonIF() {
			@Override public String getLabel() { return "Select Next"; }
			@Override public EventHandler<ActionEvent> getEventHandler() {
				return new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						listView.getSelectionModel().selectNext();
					}
				};
			}
		},
		new ButtonIF() {
			@Override public String getLabel() { return "Select Previous"; }
			@Override public EventHandler<ActionEvent> getEventHandler() {
				return new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						listView.getSelectionModel().selectPrevious();
					}
				};
			}
		}
	};

}
