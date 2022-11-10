package com.github.mbeier1406.javafx.layout;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Demonstriert Möglichkeiten des Styles und Layouts für die {@linkplain TextFlow}.
 * @author mbeier
 */
public class TextFlowLayout extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		final Text t1 = new Text("Die Java-Technik (englisch Java Technology) ist eine ursprünglich von Sun (heute Oracle-Gruppe) entwickelte Sammlung von Spezifikationen, "),
				t2 = new Text("die zum einen die Programmiersprache Java und zum anderen verschiedene Laufzeitumgebungen für Computerprogramme definieren"),
				t3 = new Text("Diese Computerprogramme werden meistens in Java geschrieben."),
				t4 = new Text("Zur Java-Technik gehören die folgenden Bestandteile:");
		final var textFlow = new TextFlow();
		textFlow.getChildren().addAll(t1, t2, t3, t4);
		textFlow.setLineSpacing(5.0);
		textFlow.setTextAlignment(TextAlignment.LEFT);
		final var scene = new Scene(textFlow, 400, 400);
		stage.setScene(scene);
		stage.show();
		
	}

	public static final void main(String[] args) {
		launch(args);
	}

}
