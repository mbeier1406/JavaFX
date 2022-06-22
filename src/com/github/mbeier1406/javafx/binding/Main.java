package com.github.mbeier1406.javafx.binding;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Demonstriert die Verwendung von 
 * <p>
 * Ein einfaches Beispiel in dem die Position und die Größe eines Objektes von der Größe der
 * Szene abhängig ist und dynamisch neu "berechnet" wird.
 * </p>
 * @author mbeier
 */
public class Main extends Application {

	/** Erzeugt ein Fenster mit einer Szene mit einem graphisches Objekt und berechnet die Größe anhand der Stage. */
	@Override
	public void start(final Stage stage) throws Exception {
		final var pane = new Pane();

		/* Kreis mit dynamischer Größe */
		final var circle = new Circle();
		circle.setFill(Color.BLACK);
		circle.centerXProperty().bind(pane.widthProperty().divide(2));
		circle.centerYProperty().bind(pane.heightProperty().divide(2));
		final var paneDimensionChangeListener = new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number alt, Number neu) {
				System.out.println("Width: "+pane.widthProperty().get()+"; Height: "+pane.heightProperty().get()+"; alt="+alt+"; neu="+neu);
				circle.radiusProperty().bind((pane.widthProperty().get() > pane.heightProperty().get() ? pane.heightProperty() : pane.widthProperty()).divide(4));
			}
		};
		pane.widthProperty().addListener(paneDimensionChangeListener);
		pane.heightProperty().addListener(paneDimensionChangeListener);
		pane.getChildren().add(circle);

		/* Textfeld und Label verbinden */
		final var textField = new TextField();
		pane.getChildren().add(textField);
		TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
		    String text = change.getText();
		    if (text.matches("[0-9]*")) {
		        return change;
		    }
		    return null;
		});
		textField.setTextFormatter(textFormatter);
		textField.setLayoutX(200);
		final var label = new Label();
		final var stringBinding = new StringBinding() {
		    { super.bind(textField.textProperty()); }
		    @Override
		    protected String computeValue() {
		    	try {
		    		return "Das doppelte ist: " + Integer.parseInt(textField.getText()) * 2;
		    	}
		    	catch ( Exception e ) { // falls das Textfeld leer ist
		    		return "";
		    	}
		    }
		};
		label.setLayoutY(3);
		label.textProperty().bind(stringBinding);
		pane.getChildren().add(label);

		final var scene = new Scene(pane, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Anwendung starten.
	 * @param args wird nicht verwendet
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
