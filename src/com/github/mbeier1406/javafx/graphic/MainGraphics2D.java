package com.github.mbeier1406.javafx.graphic;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Zeigt die Verwendung von 2D-Grafiken.
 * @author mbeier
 */
public class MainGraphics2D extends Application {

	/** {@inheritDoc} */
	@Override
	public void start(Stage stage) throws Exception {

		final var rectangle = new Rectangle(200, 150, 100, 120);
		rectangle.setArcWidth(20);
		rectangle.setArcHeight(30);
		rectangle.setFill(Color.BLACK);
		final var circle = new Circle(100, 100, 30, Color.DARKBLUE);
		final var ellipse = new Ellipse(400, 400, 50, 100);
		final var line = new Line(700, 700, 650, 650);
		line.setStroke(Color.YELLOWGREEN);
		final var text = new Text(300, 20, "Start");
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
		text.setFill(Color.BEIGE);
		text.setStrokeWidth(2);
		text.setStroke(Color.BLACK);
		final var polygon = new Polygon();
		polygon.setFill(Color.DARKRED);
		polygon.getPoints().addAll(new Double[] {500.0, 480.0, 530.0, 620.0, 480.0, 660.0, 440.0, 577.0});
		final var polyline = new Polyline();
		polyline.getPoints().addAll(new Double[] { 200.0, 50.0, 400.0, 50.0, 450.0, 150.0, 400.0, 250.0, 150.0, 150.0 });
		final var path = new Path();
		path.setStrokeWidth(4);
		path.getElements().addAll(
				new MoveTo(250, 500),
				new LineTo(150, 50),
				new CubicCurveTo(400.0, 500.0, 480.0, 520.0, 700.0, 705.0));
		final var shape1 = Shape.union(new Circle(400, 650, 50, Color.DARKRED), new Circle(425, 650, 50, Color.DARKGREEN));
		final var shape2 = Shape.intersect(new Circle(100, 650, 50, Color.DARKRED), new Circle(125, 650, 50, Color.DARKGREEN));
		final var shape3 = Shape.subtract(new Circle(650, 650, 50, Color.DARKRED), new Circle(625, 650, 50, Color.DARKGREEN));

		final var group = new Group();
		group.getChildren().addAll(rectangle, circle, ellipse, line, text, polygon, polyline, path, shape1, shape2, shape3);

		final var scene = new Scene(group, 800, 800);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
