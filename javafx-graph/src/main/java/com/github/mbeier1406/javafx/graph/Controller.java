package com.github.mbeier1406.javafx.graph;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;

/**
 * Controller für {@code Application.fxml}.
 */
public class Controller implements Initializable {

	public static final Logger LOGGER = LogManager.getLogger();

	@FXML
	private BorderPane borderPane;

	@FXML
	private Canvas canvas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.info("Controller wird initialisiert: url={}; resouces={}", location, resources);
		var konfiguration = new KoordinatenSystem.Konfiguration.KonfigurationBuilder()
				.withXVon(-5)
				.withXBis(400)
				.withYVon(-1.5)
				.withYBis(20)
				.build();
			new KoordinatenSystem(Screen.getPrimary(), this.canvas, konfiguration).zeichnen();
	}

	/** Anwendung beenden */
	@FXML
    void closeApplication(ActionEvent event) {
		LOGGER.info("Anwendung wird beendet.");
		Platform.exit();
    }

//	private final Map<String, Function<Double, Double>> VORDEFINIERTE_KURVEN = new;
	/** Vordefinierte Kurve zeichnen */
	@FXML
    void vordefinierteKurveZeichnen(ActionEvent event) {
		LOGGER.trace("event={}", ((MenuItem) event.getSource()).getText());
    }

}
