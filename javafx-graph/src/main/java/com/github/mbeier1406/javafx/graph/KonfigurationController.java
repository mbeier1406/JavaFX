package com.github.mbeier1406.javafx.graph;

import static com.github.mbeier1406.javafx.graph.Commons.BENUTZER_FEHLER;
import static com.github.mbeier1406.javafx.graph.Commons.alertShowAndWait;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

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
	private TextField linienBreiteTextField;

	@FXML
    private Button buttonAbbrechen;


	/** Textfelder konfigurieren */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		xAchseVonTextField.textProperty().addListener(doubleChecker);
		xAchseBisTextField.textProperty().addListener(doubleChecker);
		yAchseVonTextField.textProperty().addListener(doubleChecker);
		yAchseBisTextField.textProperty().addListener(doubleChecker);
		linienBreiteTextField.textProperty().addListener(doubleChecker);
	}


	/** Konfiguration wird gelöscht */
	@FXML
	void buttonKonfigurationLoeschenAktiviert(ActionEvent event) {
		this.konfiguration = null;
	}

	/** Setzt die Konfiguration auf den Standard zurück */
	@FXML
	void buttonStandardKonfigurationAktiviert(ActionEvent event) {
		this.konfiguration = new Konfiguration.KonfigurationBuilder().build();
	}

	/** Schließt das Konfigurationsfenster - Konfiguration wird aus den Textfeldern entnommen */
	@FXML
	void buttonKonfigurationUebernehmenAktiviert(ActionEvent event) {
		try {
			this.konfiguration = new Konfiguration.KonfigurationBuilder(this.konfiguration)
					.withXVon(Double.parseDouble(xAchseVonTextField.getText()))
					.withXBis(Double.parseDouble(xAchseBisTextField.getText()))
					.withYVon(Double.parseDouble(yAchseVonTextField.getText()))
					.withYBis(Double.parseDouble(yAchseBisTextField.getText()))
					.withLineWidth(Double.parseDouble(linienBreiteTextField.getText()))
					.build();
			((Stage) buttonAbbrechen.getScene().getWindow()).close();
		}
		catch ( NumberFormatException e ) {
			alertShowAndWait(
					BENUTZER_FEHLER,
					"Ungültige EIngabe!",
					"Bitte korrekte Zahlen eingeben: '"+e.getLocalizedMessage()+"'");
		}
	}

	/** Schließt das Konfigurationsfenster - Konfiguration bleibt wie bisher */
	@FXML
	void buttonAbbrechenAktiviert(ActionEvent event) {
		((Stage) buttonAbbrechen.getScene().getWindow()).close();
	}


	/** Die eigene {@linkplain Konfiguration} wenn gesetzt */
	private Konfiguration konfiguration = new Konfiguration.KonfigurationBuilder().build();

	/** Liefert die {@linkplain Konfiguration} für den Graphen an den {@linkplain Controller} geben */
	public Optional<Konfiguration> getKonfiguration() {
		return Optional.ofNullable(this.konfiguration);
	}

	/** Setzt die in {@linkplain Controller} verwendete {@linkplain Konfiguration} zur Bearbeitung */
	public void setKonfiguration(final Optional<Konfiguration> konfiguration) {
		this.konfiguration = konfiguration.orElseGet(() -> new Konfiguration.KonfigurationBuilder().build());
		var k = this.konfiguration;
		xAchseVonTextField.setText(String.valueOf(k.getxVon()));
		xAchseBisTextField.setText(String.valueOf(k.getxBis()));
		yAchseVonTextField.setText(String.valueOf(k.getyVon()));
		yAchseBisTextField.setText(String.valueOf(k.getyBis()));
		linienBreiteTextField.setText(String.valueOf(k.getLineWidth()));
	}


	/** Stellt sicher, dass in den betroffenen Textfeldern nur valide Daten vom Typ <b>&lt;T></b> eingegeben werden können */
	public static class TextFieldTypeChecker<T> {
		public ChangeListener<String> getTypeChecker(Function<String, T> f) {
			return (observable, oldValue, newValue) -> {
				var stringProp = ((StringProperty) observable);
				try {
					f.apply(stringProp.get());
				}
				catch ( Exception e ) {
					stringProp.set("");
				}
			};
		};
	}

	/* Listener, um den Typ von Textfeldern zu prüfen */
	final ChangeListener<String> doubleChecker = new TextFieldTypeChecker<Double>().getTypeChecker(Double::parseDouble);
	final ChangeListener<String> integerChecker = new TextFieldTypeChecker<Integer>().getTypeChecker(Integer::parseInt);

}
