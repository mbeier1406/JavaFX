package com.github.mbeier1406.javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

		final var image = new Image(getClass().getResourceAsStream("../Images/Cid.jpg"));
		final var imageView = new ImageView(image);
		imageView.setFitHeight(image.getHeight()/6);
		imageView.setFitWidth(image.getWidth()/6);

		final Slider opacitySlider = new Slider(0, 1, 1), sepiaSlider = new Slider(0, 1, 1), scaleSlider = new Slider(0.5, 1, 1);
		opacitySlider.setShowTickLabels(true);
		opacitySlider.setShowTickMarks(true);
		opacitySlider.setMajorTickUnit(0.5);
		opacitySlider.setMinorTickCount(4);

		final Label labelOpacity = new Label("Opacity Level"), labelSepia = new Label("Sepia Tone"), labelScale = new Label("Scale Factor"),
				opacityValue = new Label(String.valueOf(opacitySlider.getValue())),
				sepiaValue = new Label(String.valueOf(sepiaSlider.getValue())),
				scaleValue = new Label(String.valueOf(scaleSlider.getValue()));

		final var gridPane = new GridPane();
		gridPane.add(imageView, 1, 0);
		gridPane.add(labelOpacity, 0, 1); gridPane.add(labelSepia, 0, 2); gridPane.add(labelScale, 0, 3);
		gridPane.add(opacitySlider, 1, 1); gridPane.add(sepiaSlider, 1, 2); gridPane.add(scaleSlider, 1, 3);
		gridPane.add(opacityValue, 2, 1); gridPane.add(sepiaValue, 2, 2); gridPane.add(scaleValue, 2, 3);
	
		final var scene = new Scene(gridPane, 600, 400);
		scene.setRoot(gridPane);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
