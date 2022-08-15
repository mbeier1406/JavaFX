package com.github.mbeier1406.javafx.controls;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Demonstriert die Verwendung von UI Controls.
 * <p>
 * Ein einfaches Beispiel in dem die Nutzung einiger Controls
 * wie Labels, Buttons usw. gezeigt wird.
 * </p>
 * @author mbeier
 */
public class Main extends Application {

	/** Das im Programm verwendete Image ist {@value} */
	private static final String IMAGES_GITHUB_PNG = "../Images/github.png";

	/**
	 * {@inheritDoc}
	 * <p>Erzeugt eine Anwendung, die diverse Controls in einem Fenster darstellt.</p>
	 */
	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("Demonstration Controls");
		final var gridPane = new GridPane();
		gridPane.setHgap(15);
		gridPane.setVgap(10);
		gridPane.add(getLabelExample(stage), 0, 0);
		gridPane.add(getButtonExample(stage), 1, 0);
		gridPane.add(getTextfieldExample(stage), 2, 0);
		final var scene = new Scene(gridPane, 1200, 400);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Demonstriert die Verwendung von Labeln:
	 * <ol>
	 * <li>Mit Text und Farbe</li>
	 * <li>Mit Text und Bild</li>
	 * <li>Nur mit Bild und Skalierungseffekt</li>
	 * </ol>
	 * @param stage die Primary Stage
	 * @return das Layout mit den Labeln
	 * @see #start(Stage)
	 */
	private Pane getLabelExample(final Stage stage) {
		final var vBox = new VBox(5); // vBox.setSpacing(5);
		final var label1 = new Label("Demonstriert die Nutzung von Farbe und Fonts");
		label1.setTextFill(Color.web("#00f0a3"));
		label1.setFont(new Font("Helvetica", 20));
		final var label2 = new Label("GitHub", new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		final var label3 = new Label();
		label3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		label3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				label3.setScaleX(1.5); label3.setScaleY(1.5);
				label3.setTranslateX(20); label3.setTranslateY(20);
			}
		});
		label3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				label3.setScaleX(1); label3.setScaleY(1);
				label3.setTranslateX(0); label3.setTranslateY(0);
			}
		});
		vBox.getChildren().addAll(label1, label2, label3);
		return vBox;
	}

	/**
	 * Demonstriert die Nutzung verschiedener Buttons.
	 * <ol>
	 * <li>Button mit Aktion X-Achse verschieben</li>
	 * <li>Radio Buttons mit Aktion Label ändern</li>
	 * <li>CheckBox für den Full-Sceen Modus</li>
	 * <li>ChoiceBox als Menü</li>
	 * <li>änderbare ComboBox als Menü</li>
	 * </ol>
	 * @param stage die Primary Stage
	 * @return das layout mit den Buttons
	 * @see #start(Stage)
	 */
	private Pane getButtonExample(final Stage stage) {
		final var vBox = new VBox(5); // vBox.setSpacing(5);
		final var button1 = new Button("Verschieb mich...", new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		button1.setFont(new Font("Helvetica", 15));
		button1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				button1.setTranslateX(button1.getTranslateX() > 0 ? 0 : 50); 
			}
		});
		final var label = new Label();
		final var toggleGroup = new ToggleGroup();
		toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
				if ( toggleGroup.getSelectedToggle() != null )
					label.setText((String) toggleGroup.getSelectedToggle().getUserData());
			}
		});
		final var rb1 = new RadioButton("Auswahl 1");
		final var rb2 = new RadioButton("Auswahl 2");
		final var rb3 = new RadioButton("Auswahl 3");
		rb3.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(IMAGES_GITHUB_PNG))));
		rb3.setSelected(true);
		rb1.setToggleGroup(toggleGroup);
		rb2.setToggleGroup(toggleGroup);
		rb3.setToggleGroup(toggleGroup);
		rb1.setUserData("Erster");
		rb2.setUserData("Zweiter");
		rb3.setUserData("Dritter");
		final var rbHBox = new HBox();
		rbHBox.setPadding(new Insets(10));
		rbHBox.getChildren().addAll(rb1, rb2, rb3);
		final var checkBox = new CheckBox("Full Screen");
		checkBox.setSelected(false);
		checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				stage.setFullScreen(!stage.isFullScreen());
			}
		});
		checkBox.setTooltip(new Tooltip("Vollbildschirm auswählen"));
		final var choiceBox = new ChoiceBox<Object>();
		choiceBox.setItems(FXCollections.observableArrayList("Punkt 1", "Punkt 2", new Separator(), "Punkt 3"));
		choiceBox.setTooltip(new Tooltip("Menüauswahl"));
		choiceBox.getSelectionModel().select(0);
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				System.out.println("neu: "+newValue+" "+choiceBox.getItems().get((int) newValue));
			}
		});
		final var comboBox = new ComboBox<String>();
		comboBox.setItems(FXCollections.observableArrayList("Auswahl 1", "Auswahl 2", "Auswahl 3"));
		comboBox.setPromptText("Auswahl...");
		comboBox.setEditable(true);
		final var buttonAuswahl = new Button("Auswahl");
		final var labelAuswahl = new Label();
		labelAuswahl.setFont(new Font("Helvetica", 20));
		buttonAuswahl.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				labelAuswahl.setText(comboBox.getValue());
			}
		});
		final var cbHBox = new HBox(5);
		cbHBox.setPadding(new Insets(10));
		cbHBox.getChildren().addAll(comboBox, buttonAuswahl, labelAuswahl);

		vBox.getChildren().addAll(button1, rbHBox, label, checkBox, choiceBox, cbHBox);
		return vBox;
	}

	/**
	 * Demonstriert die Nutzung von Textfeldern.
	 * <ol>
	 * <li></li>
	 * </ol>
	 * @param stage die Primary Stage
	 * @return das layout mit den Textfeldern
	 * @see #start(Stage)
	 */
	private Pane getTextfieldExample(final Stage stage) {
		final var gridPane = new GridPane();
		gridPane.setPadding(new Insets(15, 5, 15, 5));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		var textFieldName = new TextField("Name...");
		textFieldName.setPrefColumnCount(10);
		var textFieldPasswort = new PasswordField();
		textFieldPasswort.setPrefColumnCount(10);
		var buttonAnzeigen = new Button("Anzeigen");
		buttonAnzeigen.setMaxSize(100, 100);
		var buttonClear = new Button("Clear");
		buttonClear.setMaxSize(100, 100);
		var label = new Label();

		gridPane.add(textFieldName, 0, 0);
		gridPane.add(textFieldPasswort, 0, 1);
		gridPane.add(buttonAnzeigen, 1, 0);
		gridPane.add(buttonClear, 1, 1);
		gridPane.add(label, 0, 2);

		buttonAnzeigen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				label.setText(textFieldPasswort.getText());
			}
		});
		buttonClear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				textFieldName.clear();
				textFieldPasswort.clear();
				label.setText("");
			}
		});

		return gridPane;
	}

	/**
	 * Startet die Anwendung.
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
