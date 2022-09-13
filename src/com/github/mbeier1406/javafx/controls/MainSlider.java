package com.github.mbeier1406.javafx.controls;

import static java.lang.String.format;
import static java.util.Locale.GERMAN;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Demonstriert die Nutzung von Slidern.
 * @author mbeier
 */
public class MainSlider extends Application {

	@Override
	public void start(final Stage stage) throws Exception {

		/* Bild bereitstellen */
		final var sepiaTone = new SepiaTone(0);
		final var image = new Image(getClass().getResourceAsStream("../Images/Cid.jpg"));
		final var imageView = new ImageView(image);
		imageView.setFitHeight(image.getHeight()/6);
		imageView.setFitWidth(image.getWidth()/6);
		imageView.setEffect(sepiaTone);

		/* Labels zur Anzeige der Slider-Werte */
		final Label labelOpacity = new Label("Opacity Level"), labelSepia = new Label("Sepia Tone"), labelScale = new Label("Scale Factor"),
				opacityValue = new Label("1.0"),
				sepiaValue = new Label("0.0"),
				scaleValue = new Label("1.0");

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

		/* Elemente zusammenbauen */
		final var gridPane = new GridPane();
		gridPane.add(imageView, 1, 0);
		gridPane.add(labelOpacity, 0, 1); gridPane.add(labelSepia, 0, 2); gridPane.add(labelScale, 0, 3);
		gridPane.add(opacitySlider, 1, 1); gridPane.add(sepiaSlider, 1, 2); gridPane.add(scaleSlider, 1, 3);
		gridPane.add(opacityValue, 2, 1); gridPane.add(sepiaValue, 2, 2); gridPane.add(scaleValue, 2, 3);
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(10));

		/* Anwendung fertigstellen */
		final var scene = new Scene(gridPane, 600, 400);
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
