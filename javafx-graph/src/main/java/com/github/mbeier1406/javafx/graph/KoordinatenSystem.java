package com.github.mbeier1406.javafx.graph;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 * Zeichnet das Koordinatensystem, auf dem später der Graph abgebildet wird.
 */
public class KoordinatenSystem {

	public static final Logger LOGGER = LogManager.getLogger(KoordinatenSystem.class);

	/**
	 * Wenn {@linkplain #zeichnen()} aufgeruden wurde und die {@linkplain Konfiguration}
	 * geändert wird, muss das alte System zunächst gelöscht werden.
	 */
	@SuppressWarnings("unused") // wird später verwendet
	private boolean gezeichnet;

	/** Speichert alle relavanten Daten zum Zeichnen des Koordinatensystems (Größe, Linienbreite usw.) */
	private Konfiguration konfiguration;

	/** Der FXML-Kontroller */
	private final Controller controller;

	/** Informationen zur Bildschirmgröße */
	private final Screen screen;


	public KoordinatenSystem(final Screen screen, final Controller controller) {
		this.gezeichnet = false;
		this.konfiguration = new Konfiguration.KonfigurationBuilder().build();
		this.screen = screen;
		this.controller = controller;
	}

	/** Zeichnet das Koordinatensystem */
	public void zeichnen() {
		LOGGER.info("konfiguration={}", this.konfiguration);
		var canvas = this.controller.getCanvas();
		double screenWidth = this.screen.getVisualBounds().getWidth();
		double screenHeight = this.screen.getVisualBounds().getHeight();
		double width = screenWidth-screenWidth*0.005;	// View X
		double height = screenHeight-screenHeight*0.05;	// View Y
		canvas.setWidth(width);
		canvas.setHeight(height);
		var gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.WHITE);
		gc.setFont(new Font(gc.getFont().getName(), gc.getFont().getSize()*1.5));
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setLineWidth(this.konfiguration.getLineWidth());

		// Model X und Model Y
		double modelWidth = this.konfiguration.getxBis() - this.konfiguration.getxVon();
		double modelHeight = this.konfiguration.getyBis() - this.konfiguration.getyVon();

		// Höhe der X-Achse und Weite der Y-Achse berechnen
		double faktorXAchse = Math.abs(modelHeight / this.konfiguration.getyVon());
		int xAchseHeight = (int) (height-height*faktorXAchse/100.0);
		double faktorYAchse = Math.abs(modelWidth / this.konfiguration.getxVon());
		int yAchseWidth = (int) (width*faktorYAchse/100.0);
		LOGGER.info("Bereiche: sichtbar={}/{}; model={}/{}, faktorXAchse={}=>{}, faktoryAchse={}=>{}",
				width, height, modelWidth, modelHeight, faktorXAchse, xAchseHeight, faktorYAchse, yAchseWidth);

		/* X-Achse von-bis */
		Point xVon = new Point(0, xAchseHeight);
		Point xBis = new Point((int) width, xAchseHeight);
		achseZeichnen(gc, "X", xVon, xBis, -10, -10, -10, 10, -30, +20);

		/* Y-Achse von-bis */
		Point yVon = new Point(yAchseWidth, (int) height);
		Point yBis = new Point(yAchseWidth, 0);
		achseZeichnen(gc, "Y", yVon, yBis, -10, +10, +10, +10, -20, +30);

		this.gezeichnet = true;
	}

	/** Zeichnet und beschriftet X- oder Y-Achse */
	private void achseZeichnen(GraphicsContext gc, String achse, Point von, Point bis, int pfeil1X, int pfeil1Y, int pfeil2X, int pfeil2Y, int textX, int textY) {
		LOGGER.info("{}-Achse {} {}", achse, von, bis);
		gc.strokeLine(von.getX(), von.getY(), bis.getX(), bis.getY());
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil1X, bis.getY()+pfeil1Y);
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil2X, bis.getY()+pfeil2Y);
		gc.strokeText(achse, bis.getX()+textX, bis.getY()+textY);
	}


	/** Speichert die Konfiguration der X/Y-Achse */
	public static class Konfiguration {
		private double xVon = -500.0, xBis = 2_000.0, yVon = -500.0, yBis = 2_000.0, lineWidth = 1.0;
		public Konfiguration() { }
		public double getxVon() {
			return xVon;
		}
		public void setxVon(double xVon) {
			this.xVon = xVon;
		}
		public double getxBis() {
			return xBis;
		}
		public void setxBis(double xBis) {
			this.xBis = xBis;
		}
		public double getyVon() {
			return yVon;
		}
		public void setyVon(double yVon) {
			this.yVon = yVon;
		}
		public double getyBis() {
			return yBis;
		}
		public void setyBis(double yBis) {
			this.yBis = yBis;
		}
		public double getLineWidth() {
			return lineWidth;
		}
		public void setLineWidth(double lineWidth) {
			this.lineWidth = lineWidth;
		}

		@Override
		public String toString() {
			return "Konfiguration [xVon=" + xVon + ", xBis=" + xBis + ", yVon=" + yVon + ", yBis=" + yBis
					+ ", lineWidth=" + lineWidth + "]";
		}

		public static class KonfigurationBuilder {
			private Konfiguration konfiguration;
			public KonfigurationBuilder() {
				this.konfiguration = new Konfiguration();
			}
			public void withXVon(double xVon) {
				this.konfiguration.setxVon(xVon);
			}
			public void withXBis(double xBis) {
				this.konfiguration.setxBis(xBis);
			}
			public void withYVon(double yVon) {
				this.konfiguration.setyVon(yVon);
			}
			public void withYBis(double yBis) {
				this.konfiguration.setyBis(yBis);
			}
			public Konfiguration build() {
				return this.konfiguration;
			}
		}
	}

}
