package com.github.mbeier1406.javafx.controls;

import static java.lang.String.format;
import static java.util.Locale.GERMAN;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Demonstriert die Nutzung von Slidern.
 * @author mbeier
 */
public class MainSlider extends Application {

	/** URL zu diesem Code ist {@value} */
	private static final String CODE_URL = "https://github.com/mbeier1406/JavaFX/blob/master/src/com/github/mbeier1406/javafx/controls/MainSlider.java";

	@Override
	public void start(final Stage stage) throws Exception {

		/* Bild bereitstellen */
		final var sepiaTone = new SepiaTone(0);
		final var image = new Image(getClass().getResourceAsStream("../Images/Cid.jpg"));
		final var imageView = new ImageView(image);
		imageView.setFitHeight(image.getHeight()/6);
		imageView.setFitWidth(image.getWidth()/6);
		imageView.setEffect(sepiaTone);

		/* Separator als Teiler */
		final var separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);
		separator.setStyle("-fx-border-width: 1px;"
				+ "-fx-border-style: dotted;");

		/* Labels zur Anzeige der Slider-Werte und Tooltip */
		final Label labelOpacity = new Label("Opacity Level"), labelSepia = new Label("Sepia Tone"), labelScale = new Label("Scale Factor"),
				opacityValue = new Label("1.0"),
				sepiaValue = new Label("0.0"),
				scaleValue = new Label("1.0");
		final var tooltip = new Tooltip("Skalierungsfaktor");
		tooltip.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Images/exit.png"))));
		scaleValue.setTooltip(tooltip);

		/* Slider um die Effekte auf das Bild anzuwenden */
		final Slider opacitySlider = new Slider(0, 1, 1), sepiaSlider = new Slider(0, 1, 0), scaleSlider = new Slider(0.5, 1, 1);
		opacitySlider.setShowTickLabels(true);
		opacitySlider.setShowTickMarks(true);
		opacitySlider.setMajorTickUnit(0.5);
		opacitySlider.setMinorTickCount(4);
		opacitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				imageView.setOpacity(newValue.doubleValue());
				opacityValue.setText(format(GERMAN, "%.1f", newValue));
			}
		});
		sepiaSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sepiaTone.setLevel(newValue.doubleValue());
				sepiaValue.setText(format(GERMAN, "%.1f", newValue));
			}
		});
		scaleSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				scaleValue.setText(format(GERMAN, "%.1f", newValue));
				imageView.setScaleX(newValue.doubleValue());
				imageView.setScaleY(newValue.doubleValue());
			}
		});

		/* Hyperlink zum Code */
		final var hyperlink = new Hyperlink("Zum Code");
		hyperlink.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getHostServices().showDocument(CODE_URL);
			}			
		});

		/* Elemente zusammenbauen */
		final var gridPane = new GridPane();
		gridPane.add(imageView, 1, 0);
		gridPane.add(separator, 1, 1);
		gridPane.add(labelOpacity, 0, 2); gridPane.add(labelSepia, 0, 3); gridPane.add(labelScale, 0, 4);
		gridPane.add(opacitySlider, 1, 2); gridPane.add(sepiaSlider, 1, 3); gridPane.add(scaleSlider, 1, 4);
		gridPane.add(opacityValue, 2, 2); gridPane.add(sepiaValue, 2, 3); gridPane.add(scaleValue, 2, 4);
		gridPane.add(hyperlink, 2, 5);
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(10));

		/* Anwendung fertigstellen */
		final var scene = new Scene(gridPane, 650, 400);
		scene.setRoot(gridPane);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Cids Mittagsschlaf");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
