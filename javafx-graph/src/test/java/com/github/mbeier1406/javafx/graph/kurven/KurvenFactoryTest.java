package com.github.mbeier1406.javafx.graph.kurven;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests für die Klasse {@linkplain KurvenFactory}.
 */
public class KurvenFactoryTest {

	/** Stellt sicher, dass die Kurven für die vordefinierten Graphen geladen werden können */
	@Test
	public void testeKurvenLaden() {
		Map<String, Kurvendefinition> kurven = KurvenFactory.getKurven();
		assertThat(kurven, notNullValue());
		assertThat(kurven.size(), greaterThan(0));
		assertThat(kurven.get("Wurzel"), notNullValue());
	}

}
