package com.github.mbeier1406.javafx.graph;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Ermöglicht die Definition einer eigenen Konfiguration zur Anzeige eines Graphen.
 * @see KoordinatenSystem
 * @see Konfiguration
 */
public class KonfigurationController {

	@FXML
	private TextField yAchseBisTextField;

	@FXML
	private TextField xAchseVonTextField;

	@FXML
	private TextField yAchseVonTextField;

	@FXML
	private TextField xAchseBisTextField;

    @FXML
    private Button buttonAbbrechen;

	@FXML
	void buttonKonfigurationAktiviert(ActionEvent event) {
		this.konfiguration = Optional.of(new Konfiguration.KonfigurationBuilder().build());
	}

	@FXML
	void buttonAbbrechenAktiviert(ActionEvent event) {
		this.konfiguration = Optional.empty();
		((Stage) buttonAbbrechen.getScene().getWindow()).close();
	}


	/** Die eigene {@linkplain Konfiguration} wenn gesetzt */
	private Optional<Konfiguration> konfiguration = Optional.of(new Konfiguration.KonfigurationBuilder().build());

	/** {@linkplain Konfiguration} für den Graphen an den {@linkplain Controller} geben */
	public Optional<Konfiguration> getKonfiguration() {
		return this.konfiguration;
	}

}
