package com.github.mbeier1406.javafx.graph.kurven;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Klassen mit dieser Annotation werden als vordefinierte
 * Kurve für die Anzeige des Graphen angeboten.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Kurve {
	/** Der Name entspricht dem Menüpunkt File -> Kurven in {@code Application.fxml} */
	public String name() default "";
}
