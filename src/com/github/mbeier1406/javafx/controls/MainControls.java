package com.github.mbeier1406.javafx.controls;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Demonstriert die Verwendung von UI Controls.
 * <p>
 * Ein einfaches Beispiel in dem die Nutzung einiger Controls
 * wie Labels, Buttons usw. gezeigt wird.
 * </p>
 * @author mbeier
 */
public class MainControls extends Application {

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
		gridPane.add(getLabelExample(), 0, 0);
		gridPane.add(getButtonExample(stage), 1, 0);
		gridPane.add(getTextfieldExample(), 2, 0);
		gridPane.add(getScrollbarExample(), 3, 0);
		gridPane.add(getPickerExample(), 0, 1);
		gridPane.add(getTitledPaneExample(), 1, 1);
		final var scene = new Scene(gridPane, 1200, 600);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
		stage.setIconified(false);
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
	private Pane getLabelExample() {
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
	 * <ol><li>Es werden Eingabe- und Passwortfeldern gezeigt</li></ol>
	 * @param stage die Primary Stage
	 * @return das Layout mit den Textfeldern
	 * @see #start(Stage)
	 */
	private Pane getTextfieldExample() {
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
	 * Demonstriert die Nutzung von Scrollbars.
	 * <ol>
	 * <li>Anzeige der Position eines Scrollbars</li>
	 * </ol>
	 * @param stage die Primary Stage
	 * @return das Layout mit den Scrollbars
	 * @see #start(Stage)
	 */
	private Pane getScrollbarExample() {

		/*  Beispiel I */
		final var hBox = new HBox();
		ScrollBar scrollBar = new ScrollBar();
		scrollBar.setOrientation(Orientation.VERTICAL);
		scrollBar.setValue(10);
		Label label = new Label("Wert: 10");
		scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number neu, Number alt) {
				label.setText("Wert: " + (int) scrollBar.getValue());
			}
		});
		hBox.getChildren().addAll(scrollBar, label);

		/* Beispiel II */
		final DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.BLACK);
		dropShadow.setOffsetX(10);
		dropShadow.setOffsetY(10);
		final var vBox = new VBox();
		vBox.setLayoutX(5);
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(20));
		IntStream.range(0, 4).forEach(i -> {
			final ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("../Images/bild"+i+".jpg")));
			imageView.setEffect(dropShadow);
			vBox.getChildren().add(imageView);
		});
		final var scrollBar2 = new ScrollBar();
		scrollBar2.setOrientation(Orientation.VERTICAL);
		scrollBar2.setLayoutX(400 - scrollBar2.getWidth());
		scrollBar2.setPrefHeight(400);
		scrollBar2.setMax(600);
		scrollBar2.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				vBox.setLayoutY(-newValue.doubleValue());
			}
		});
		final var group = new Group();
		group.getChildren().addAll(vBox, scrollBar2);
		final var stage = new Stage();
		final var scene = new Scene(group, 400, 400);
		stage.setScene(scene);
		stage.show();
		stage.requestFocus();

		return hBox;
	}

	/**
	 * Zeigt die Nutzung von verschiedenen Pickern.
	 * @return die {@linkplain HBox} mit dem {@linkplain ColorPicker}
	 * und dem Kreis mit der aktuell ausgewählten Farbe, {@linkplain DatePicker} usw.
	 */
	public Node getPickerExample() {
		final var vBox = new VBox(5);
		final var hBoxColor = new HBox();
		final var circle = new Circle(50);
		final var colorPicker = new ColorPicker(Color.BLACK);
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				circle.setFill(colorPicker.getValue());
			}
		});
		hBoxColor.getChildren().addAll(colorPicker, circle);
		final var hBoxDate = new HBox();
		final var label = new Label(String.valueOf(LocalDate.now()));
		Locale.setDefault(Locale.FRENCH);
		StringConverter<LocalDate> stringConverter = new StringConverter<LocalDate>() {
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			@Override
			public LocalDate fromString(String arg0) {
				return arg0 == null ? null : ( !arg0.isEmpty() ? LocalDate.parse(arg0, format) : null );
			}
			@Override
			public String toString(LocalDate arg0) {
				return arg0 == null ? "" : format.format(arg0);
			}
		};
		final var datePicker = new DatePicker(LocalDate.now());
		datePicker.setShowWeekNumbers(true);
		datePicker.setConverter(stringConverter);
		datePicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				label.setText(String.valueOf(datePicker.getValue()));
			}
		});
		hBoxDate.getChildren().addAll(datePicker, label);
		vBox.getChildren().addAll(hBoxColor, hBoxDate);
		return vBox;
	}

	/**
	 * Erzeugt eine {@linkplain TitledPane} mit Texten und Eingabefeldern.
	 * @return die TitledPane
	 */
	public Node getTitledPaneExample() {
		final var titledPane = new TitledPane();
		titledPane.setText("Auswahl");
		final var gridPane = new GridPane();
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setPadding(new Insets(5));
		gridPane.add(new Label("Eingabe1:"), 0, 0);
		gridPane.add(new TextField(), 1, 0);
		gridPane.add(new Label("Eingabe2:"), 0, 1);
		gridPane.add(new TextField(), 1, 1);
		gridPane.add(new Label("Eingabe3:"), 0, 2);
		gridPane.add(new TextField(), 1, 2);
		titledPane.setContent(gridPane);
		return titledPane;
	}

	/**
	 * Startet die Anwendung.
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
