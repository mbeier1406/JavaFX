package com.github.mbeier1406.javafx.graph;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

/**
 * Controller für {@code Application.fxml}.
 */
public class Controller {

	public static final Logger LOGGER = LogManager.getLogger();

	@FXML
	private BorderPane borderPane;

	@FXML
	private Canvas canvas;

	@FXML
    void closeApplication(ActionEvent event) {
		LOGGER.info("Anwendung wird beendet.");
		Platform.exit();
    }

	public Canvas getCanvas() {
		return canvas;
	}

}
