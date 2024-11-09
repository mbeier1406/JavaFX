package com.github.mbeier1406.javafx.graph;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Ermöglicht die Definition einer eigenen Konfiguration zur Anzeige eines Graphen.
 * @see KoordinatenSystem
 * @see Konfiguration
 */
public class KonfigurationController implements Initializable {

	@FXML
	private TextField xAchseVonTextField;

	@FXML
	private TextField yAchseBisTextField;

	@FXML
	private TextField yAchseVonTextField;

	@FXML
	private TextField xAchseBisTextField;

    @FXML
    private Button buttonAbbrechen;


	@FXML
	void buttonKonfigurationLoeschenAktiviert(ActionEvent event) {
		this.konfiguration = Optional.empty();
	}

	@FXML
	void buttonStandardKonfigurationAktiviert(ActionEvent event) {
		this.konfiguration = Optional.of(new Konfiguration.KonfigurationBuilder().build());
	}

	@FXML
	void buttonKonfigurationUebernehmenAktiviert(ActionEvent event) {
		this.konfiguration = Optional.of(
			new Konfiguration.KonfigurationBuilder()
				.withXVon(Double.parseDouble(xAchseVonTextField.getText()))
				.withXBis(Double.parseDouble(xAchseBisTextField.getText()))
				.withYVon(Double.parseDouble(yAchseVonTextField.getText()))
				.withYBis(Double.parseDouble(yAchseBisTextField.getText()))
				.build());
		((Stage) buttonAbbrechen.getScene().getWindow()).close();
	}

	@FXML
	void buttonAbbrechenAktiviert(ActionEvent event) {
		((Stage) buttonAbbrechen.getScene().getWindow()).close();
	}

	/** Stellt sicher, dass in den betroffenen Textfeldern nur valide {@linkplain Double} eingegeben werdne können */
	final ChangeListener<String> listenerDouble = (observable, oldValue, newValue) -> {
		var stringProp = ((StringProperty) observable);
		try {
			Double.parseDouble(stringProp.get());
		}
		catch ( Exception e ) {
			stringProp.set("");
		}
	};

	/** Textfelder konfigurieren */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		xAchseVonTextField.textProperty().addListener(listenerDouble);
		xAchseBisTextField.textProperty().addListener(listenerDouble);
		yAchseVonTextField.textProperty().addListener(listenerDouble);
		yAchseBisTextField.textProperty().addListener(listenerDouble);
	}


	/** Die eigene {@linkplain Konfiguration} wenn gesetzt */
	private Optional<Konfiguration> konfiguration = Optional.of(new Konfiguration.KonfigurationBuilder().build());

	/** Liefert die {@linkplain Konfiguration} für den Graphen an den {@linkplain Controller} geben */
	public Optional<Konfiguration> getKonfiguration() {
		return this.konfiguration;
	}

	/** Setzt die in {@linkplain Controller} verwendete {@linkplain Konfiguration} zur Bearbeitung */
	public void setKonfiguration(final Optional<Konfiguration> konfiguration) {
		this.konfiguration = konfiguration;
		if ( konfiguration.isPresent() ) {
			var k = konfiguration.get();
			xAchseVonTextField.setText(String.valueOf(k.getxVon()));
			xAchseBisTextField.setText(String.valueOf(k.getxBis()));
			yAchseVonTextField.setText(String.valueOf(k.getyVon()));
			yAchseBisTextField.setText(String.valueOf(k.getyBis()));
		}
	}

}
