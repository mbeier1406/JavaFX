package com.github.mbeier1406.javafx.graphic;

import java.awt.Point;

import com.sun.jdi.event.Event;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
 * Zeigt die Verwendung von 2D-Grafiken und Shapes mit Drag-And-Drop.
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

		shape1.setFill(Color.DARKCYAN);
		shape1.setUserData(Cursor.HAND);
		shape1.setCursor((Cursor) shape1.getUserData());
		shape1.setOnMousePressed(shapePressed);
		shape1.setOnMouseDragged(shapeDragged);
		shape1.setOnMouseReleased(shapeReleased);

		final var group = new Group();
		group.getChildren().addAll(rectangle, circle, ellipse, line, text, polygon, polyline, path, shape1, shape2, shape3);

		final var scene = new Scene(group, 800, 800);
		stage.setScene(scene);
		stage.show();
	}

	/** {@linkplain #startPosMouse}: wo mit der Maus gedrückt wurde, {@linkplain #startPosShape} wo sich die Shape befindet */
	private Point startPosMouse, startPosShape;

	/**
	 * Behandelt den {@link Event} {@linkplain Shape#setOnMousePressed(EventHandler)}: Startpunkt für den Drag-And-Drop
	 * Vorgang: es wird sich gemerkt, wo die Maus ({@linkplain #startPosMouse} und die Shape {@linkplain #startPosShape}
	 * sich gerade befindet.
	 */
	private EventHandler<MouseEvent> shapePressed = ( event ) -> {
		startPosMouse = new Point((int) event.getSceneX(), (int) event.getSceneY());
		startPosShape = new Point((int) ((Shape) event.getSource()).getTranslateX(), (int) ((Shape) event.getSource()).getTranslateY());
	};

	/**
	 * Behandelt den {@link Event} {@linkplain Shape#setOnMouseDragged(EventHandler)}: Es wird der Cursor geändert
	 * und die Differenz zum Startevent {@linkplain #shapePressed} berechnet, und dieses Offset auf die ausgewählte Shape
	 * angewendet {@linkplain Shape#setTranslateX(double)} und {@linkplain Shape#setTranslateY(double)}.
	 */
	private EventHandler<MouseEvent> shapeDragged = ( event ) -> {
		Shape shape = ((Shape) event.getSource());
		shape.setCursor(Cursor.CROSSHAIR);
		shape.setTranslateX(startPosShape.getX()+(event.getSceneX()-startPosMouse.getX()));
		shape.setTranslateY(startPosShape.getY()+(event.getSceneY()-startPosMouse.getY()));
	};

	/**
	 * Behandelt den {@link Event} {@linkplain Shape#setOnMouseReleased(EventHandler)}: der
	 * Cursor wird wieder auf den Originalwert zurückgesetzt.
	 */
	private EventHandler<MouseEvent> shapeReleased = ( event ) -> {
		Shape shape = ((Shape) event.getSource());
		shape.setCursor((Cursor) shape.getUserData());
	};

	public static void main(String[] args) {
		launch(args);
	}

}
