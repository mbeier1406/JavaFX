package com.github.mbeier1406.javafx.graph.kurven;

import java.util.function.Function;

import com.github.mbeier1406.javafx.graph.Konfiguration;

/**
 * Definiert die Daten, die eine vordefinierte Kurve
 * für die Anzeige bereitstelen muss.
 */
public interface Kurvendefinition {

	/** Die darzustellende Funktion, etwa {@linkplain Math#sin(double)} usw. */
	public Function<Double, Double> getFunction();

	/** Die Einstellungen zu Anzeige, Darstellungsbereich usw. */
	public Konfiguration getKonfiguration();

}
