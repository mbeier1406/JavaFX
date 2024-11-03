package com.github.mbeier1406.javafx.graph.kurven;

import java.util.function.Function;

import com.github.mbeier1406.javafx.graph.Konfiguration;
import com.github.mbeier1406.javafx.graph.Konfiguration.KonfigurationBuilder;

/**
 * Graph für die Sinus-Funktion.
 * @see Cosinus
 */
@Kurve(name = "Sinus")
public class Sinus implements Kurvendefinition {

	/** {@inheritDoc} */
	@Override
	public Function<Double, Double> getFunction() {
		return x -> Math.sin(Math.toRadians(x));
	}

	/** {@inheritDoc} */
	@Override
	public Konfiguration getKonfiguration() {
		return new KonfigurationBuilder()
				.withXVon(-360)	// Hier das
				.withYVon(-1.5)	// Korrdinatensystem
				.withXBis(360)	// einstellen
				.withYBis(1.5)
				.withXStart(-360) // Wertebereich
				.withXEnde(360)
				.withXDelta(0.1) // Schrittweite X-Achse
				.build();
	}

}
