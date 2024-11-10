package com.github.mbeier1406.javafx.graph;

import static com.github.mbeier1406.javafx.graph.Commons.INTERER_FEHLER;
import static com.github.mbeier1406.javafx.graph.Commons.alertShowAndWait;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.mbeier1406.javafx.graph.kurven.Kurve;
import com.github.mbeier1406.javafx.graph.kurven.KurvenFactory;
import com.github.mbeier1406.javafx.graph.kurven.Kurvendefinition;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

	/**
	 * Wenn eine eigene Funktion definiert wird oder nicht die Standardkonfiguration in
	 * {@linkplain #vordefinierteKurveZeichnen(ActionEvent)} verwendt werden soll.
	 */
	private Optional<Konfiguration> konfiguration = Optional.empty();

	/** Zum Start wird ein Korrdinatensystem ohne Kurve angezeigt */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LOGGER.info("Controller wird initialisiert: url={}; resouces={}", location, resources);
		this.konfiguration = Optional.of(new Konfiguration.KonfigurationBuilder()
				.withXVon(-25)
				.withXBis(400)
				.withYVon(-1.5)
				.withYBis(20)
				.build());
		new KoordinatenSystem(screen, this.canvas, konfiguration.get()).zeichnen();
	}

	/** Anwendung beenden */
	@FXML
    void closeApplication(ActionEvent event) {
		LOGGER.info("Anwendung wird beendet.");
		Platform.exit();
    }

	/**
	 * Definiert eine eigene Konfiguration zur Anzeige eines Graphen.
	 * @see #konfiguration
	 */
    @FXML
    void konfigurationDefinieren(ActionEvent event) {
    	LOGGER.info("Eigene Konfiguration wird erstellt.");
    	String fxml = "Konfiguration.fxml";
    	final var fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(getClass().getResource(fxml));
		try {
			fxmlLoader.load();
		} catch ( Exception e ) {
			LOGGER.error("FXML kann nicht geladen werden: {}", fxml, e);
			alertShowAndWait(
					INTERER_FEHLER,
					"FXML nicht geladen!",
					"Datei ist '"+fxml+"'.");
			return;
		}
		var konfigurationController = (KonfigurationController) fxmlLoader.getController();
		konfigurationController.setKonfiguration(konfiguration);
		Scene scene = new Scene(fxmlLoader.getRoot());
		Stage stage = new Stage();
		stage.setTitle("Konfiguration definieren");
		stage.setScene(scene);
		stage.showAndWait();
		stage.requestFocus();
    	this.konfiguration = konfigurationController.getKonfiguration();
    	LOGGER.info("Neue Konfiguration: {}", this.konfiguration);
    	if ( this.konfiguration.isPresent() )
    		new KoordinatenSystem(this.screen, this.canvas, this.konfiguration.get()).zeichnen();
    }

	/**
	 * Wieder die Standardkonfiguration benutzen.
	 * @see #konfiguration
	 */
    @FXML
    void konfigurationLoeschen(ActionEvent event) {
    	LOGGER.info("Standardkonfiguration wird gewählt.");
    	this.konfiguration = Optional.empty();
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
			new KoordinatenSystem(
					this.screen,
					this.canvas,
					konfiguration.orElseGet(kurvendefinition::getKonfiguration))
				.zeichnen(kurvendefinition.getFunction());
		else
			alertShowAndWait(
					INTERER_FEHLER,
					"Kurvendefinition fehlt!",
					"Für diese Bezeichnung gibt es keine Kurve: "+kurve);
    }

}
