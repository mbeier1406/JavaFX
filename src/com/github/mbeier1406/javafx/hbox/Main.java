package com.github.mbeier1406.javafx.hbox;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Für JDK > 10: --module-path /home/mbeier/Eclipse/libs/javafx-sdk-17.0.2/lib --add-modules=javafx.controls,javafx.fxml
public class Main extends Application {

	Rectangle2D screen = Screen.getPrimary().getVisualBounds();

	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = new javafx.scene.layout.HBox();
			root.setPrefSize(135, 70);
			root.setSpacing(10.0);
			root.setAlignment(Pos.CENTER);
			Button button1 = new Button(), button2 = new Button();
			button1.setPrefSize(64.0, 64.0); button1.getStyleClass().add("button1");
			button1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					getHostServices().showDocument("https://github.com/mbeier1406");
				}
			});
			button2.setPrefSize(64.0, 64.0); button2.getStyleClass().add("button2");
			button2.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.exit(0);
				}
			});
			root.getChildren().addAll(button1, button2);
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setX(screen.getWidth()/2-135/2);
			primaryStage.setY(30);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
