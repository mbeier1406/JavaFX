package com.github.mbeier1406.javafx.hbox;
	
import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.TRANSPARENT;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Kleie Anwendung zur Erzeugung eines transparenten Fensters mit zwei Buttons,
 * von denen der eine meine Webseite aufruft, und der andere die Anwendung beendet.
 * <p>Für JDK > 10: <code>--module-path /home/mbeier/Eclipse/libs/javafx-sdk-17.0.2/lib --add-modules=javafx.controls,javafx.fxml<7code></p>
 * @author mbeier
 */
public class Main extends Application {

	/** */
	Rectangle2D screen = Screen.getPrimary().getVisualBounds();

	/** Startmethode erzeugt das Fenster mit den Buttons */
	@Override
	public void start(Stage primaryStage) {
		try {
			// Layout der Szene: HBox = horizontale Anordnung der Elemente
			HBox root = new HBox();
			root.setPrefSize(135, 70);
			root.setSpacing(10.0);
			root.setAlignment(CENTER);
			// Elemente in der HBox
			Button button1 = new Button(), button2 = new Button();
			button1.setPrefSize(64.0, 64.0);
			button1.getStyleClass().add("button1");
			button1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					getHostServices().showDocument("https://github.com/mbeier1406");
				}
			});
			button2.setPrefSize(64.0, 64.0);
			button2.getStyleClass().add("button2");
			button2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});
			root.getChildren().addAll(button1, button2);
			// Scene erstellen
			Scene scene = new Scene(root);
			scene.setFill(TRANSPARENT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setX(screen.getWidth()/2-135/2); // Anwendung zentral am oberen Bildschirmrand plazieren
			primaryStage.setY(30);
			primaryStage.initStyle(StageStyle.TRANSPARENT); // Hauptfenster nicht sichtbar machen
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Anwendung starten.
	 * @param args Pprogrammparameter werden nicht verwendet.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
