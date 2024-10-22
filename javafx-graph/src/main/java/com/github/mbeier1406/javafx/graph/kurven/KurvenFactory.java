package com.github.mbeier1406.javafx.graph.kurven;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

/**
 * Stellt eine Map bereit, die für jeden Menüeintrag in <i>File -> Kurven</i> (siehe
 *  {@code /javafx-graph/src/main/resources/com/github/mbeier1406/javafx/graph/Application.fxml})
 *  die Funktion für die Kurve und die Anzeigekonfiguration definiert.
 *  @see Kurvendefinition
 */
public class KurvenFactory {

	public static Map<String, Kurvendefinition> getKurven() {
		try {
			Set<Class<?>> kurvenClasses = new Reflections("com.github.mbeier1406.javafx.graph.kurven").getTypesAnnotatedWith(Kurve.class);
			final Map<String, Kurvendefinition> kurven = new HashMap<>();
			kurvenClasses.forEach(kurvenClass -> {
				try {
					Kurvendefinition kurve = (Kurvendefinition) kurvenClass.getConstructor().newInstance();
					kurven.put(kurvenClass.getAnnotation(Kurve.class).name(), kurve);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new RuntimeException("kurvenClass="+kurvenClass, e);
				}
			});
			return kurven;
		}
		catch ( Exception e ) {
			throw new RuntimeException("Kurven können nicht mehr Refelction ermittelt werden!", e);
		}
	}

}
