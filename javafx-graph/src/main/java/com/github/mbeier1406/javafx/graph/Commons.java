package com.github.mbeier1406.javafx.graph;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Gemeinsam benutzte Funktionen
 */
public class Commons {

	/** {@value}: Überschrift für das Fehlerfenster in {@linkplain #alertShowAndWait(String, String, String)} */
	public static final String INTERER_FEHLER = "Interner Fehler";

	/** {@value}: Überschrift für das Fehlerfenster in {@linkplain #alertShowAndWait(String, String, String)} */
	public static final String BENUTZER_FEHLER = "Fehler";

	/** Fehlerfenster anzeigen und auf Bestätigung warten */
	public static void alertShowAndWait(String fehlerTyp, String fehlerKurzText, String fehlerLangText) {
		var alert = new Alert(AlertType.ERROR);
		alert.setTitle(fehlerTyp);
		alert.setHeaderText(fehlerKurzText);
		alert.setContentText(fehlerLangText);
		alert.showAndWait();
	}

}
