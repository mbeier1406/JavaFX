package com.github.mbeier1406.javafx.graph.kurven;

import java.util.function.Function;

import com.github.mbeier1406.javafx.graph.Konfiguration;
import com.github.mbeier1406.javafx.graph.Konfiguration.KonfigurationBuilder;

/**
 * Graph für die Quadrat-Funktion.
 */
@Kurve(name = "Quadrat")
public class Square implements Kurvendefinition {

	/** {@inheritDoc} */
	@Override
	public Function<Double, Double> getFunction() {
		return x -> Math.pow(x, 2);
	}

	/** {@inheritDoc} */
	@Override
	public Konfiguration getKonfiguration() {
		return new KonfigurationBuilder()
				.withXVon(-25)	// Hier das
				.withYVon(-75)	// Korrdinatensystem
				.withXBis(25)	// einstellen
				.withYBis(650)	// Wurzel aus 400
				.withXStart(-25)
				.withXEnde(10)
				.build();
	}

}
