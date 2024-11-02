package com.github.mbeier1406.javafx.graph;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.mbeier1406.javafx.graph.kurven.Kurve;
import com.github.mbeier1406.javafx.graph.kurven.KurvenFactory;
import com.github.mbeier1406.javafx.graph.kurven.Kurvendefinition;

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

	private final Screen screen = Screen.getPrimary();

	/**
	 * Enthält die Kurvendefinition (Einstellungen für das Koordinatensystem, Wertebereich der
	 * anzuzeigenden Kurve auf der X-Achse sowie die darzustellende Funktion) wobei der Key
	 * aus {@linkplain Kurve#name()} gelesen wird und dem zugehörigen Menüeintrag aus der
	 * {@linkplain /javafx-graph/src/main/resources/com/github/mbeier1406/javafx/graph/Application.fxml}
	 * Datei entsprechen muss.
	 * @see #vordefinierteKurveZeichnen(ActionEvent)
	 */
	private final Map<String, Kurvendefinition> kurven = KurvenFactory.getKurven();

	@FXML
	private BorderPane borderPane;

	@FXML
	private Canvas canvas;

	/** Zum Start wird ein Korrdinatensystem ohne Kurve angezeigt */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.info("Controller wird initialisiert: url={}; resouces={}", location, resources);
		var konfiguration = new Konfiguration.KonfigurationBuilder()
				.withXVon(-5)
				.withXBis(400)
				.withYVon(-1.5)
				.withYBis(20)
				.build();
			new KoordinatenSystem(screen, this.canvas, konfiguration).zeichnen();
	}

	/** Anwendung beenden */
	@FXML
    void closeApplication(ActionEvent event) {
		LOGGER.info("Anwendung wird beendet.");
		Platform.exit();
    }

	/**
	 * Vordefinierte Kurve zeichnen: der Text des Menüeintrags wird
	 * verwendet, um die entsprechende Defintion für den Graphen
	 * aus {@linkplain #kurven} zu lesen.
	 */
	@FXML
    void vordefinierteKurveZeichnen(ActionEvent event) {
		String kurve = ((MenuItem) event.getSource()).getText();
		final var kurvendefinition = kurven.get(kurve);
		LOGGER.trace("kurve={}", kurve);
		if ( kurvendefinition != null )
			new KoordinatenSystem(screen, this.canvas, kurvendefinition.getKonfiguration())
				.zeichnen(kurvendefinition.getFunction());			
    }

}
