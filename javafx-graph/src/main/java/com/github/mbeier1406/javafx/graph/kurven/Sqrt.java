package com.github.mbeier1406.javafx.graph.kurven;

import java.util.function.Function;

import com.github.mbeier1406.javafx.graph.Konfiguration;
import com.github.mbeier1406.javafx.graph.Konfiguration.KonfigurationBuilder;

/**
 * Graph für die Wurzel-Funktion.
 */
@Kurve(name = "Wurzel")
public class Sqrt implements Kurvendefinition {

	/** {@inheritDoc} */
	@Override
	public Function<Double, Double> getFunction() {
		return Math::sqrt;
	}

	/** {@inheritDoc} */
	@Override
	public Konfiguration getKonfiguration() {
		return new KonfigurationBuilder()
				.withXVon(-15)	// Hier das
				.withYVon(-1.5)	// Korrdinatensystem
				.withXBis(400)	// einstellen
				.withYBis(20)	// Wurzel aus 400
				.withXStart(0) // Wurzel aus negativen Zahlen nicht erlaubt
				.withAnzahlEinheitenStricheXAchse(15) // Anzahl Einheitenstriche X-Achse
				.build();
	}

}
