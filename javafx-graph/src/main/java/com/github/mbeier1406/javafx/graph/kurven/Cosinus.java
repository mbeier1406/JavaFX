package com.github.mbeier1406.javafx.graph.kurven;

import java.util.function.Function;

import com.github.mbeier1406.javafx.graph.Konfiguration;

/**
 * Graph für die Cosinus-Funktion (Radiant).
 * @see Sinus
 */
@Kurve(name = "Cosinus")
public class Cosinus implements Kurvendefinition {

	/** {@inheritDoc} */
	@Override
	public Function<Double, Double> getFunction() {
		return x -> Math.cos(Math.toRadians(x));
	}

	/** {@inheritDoc} */
	@Override
	public Konfiguration getKonfiguration() {
		/* Konfiguration analog Sinus */
		return new Sinus().getKonfiguration();
	}

}
