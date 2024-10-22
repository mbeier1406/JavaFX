package com.github.mbeier1406.javafx.graph;

import java.awt.Point;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 * Zeichnet das Koordinatensystem, auf dem später der Graph abgebildet wird.
 */
public class KoordinatenSystem {

	public static final Logger LOGGER = LogManager.getLogger(KoordinatenSystem.class);

	/** Speichert alle relavanten Daten zum Zeichnen des Koordinatensystems (Größe, Linienbreite usw.) */
	private Konfiguration konfiguration;

	/** Informationen zur Bildschirmgröße */
	private final Screen screen;

	/** Der Untergrund zum Zeichnen */
	private Canvas canvas;

	/** Definiert die Größe der View, die Maße der Anzeige,wird durch den Screen definiert */
	double viewWidth, viewHeight, screenWidth, screenHeight;

	/** Größe des Modells, wird aus der {@linkplain #konfiguration} gelesen */
	double modelWidth, modelHeight;

	/** Umrechnungsfaktor für X und Y Model -> View */
	double faktorXAchse, faktorYAchse;


	public KoordinatenSystem(final Screen screen, final Canvas canvas) {
		this(screen, canvas, new Konfiguration.KonfigurationBuilder().build());
	}
	public KoordinatenSystem(final Screen screen, final Canvas canvas, final Konfiguration konfiguration) {
		this.konfiguration = konfiguration;
		this.screen = screen;
		this.screenWidth = this.screen.getVisualBounds().getWidth();
		this.screenHeight = this.screen.getVisualBounds().getHeight();
		this.viewWidth = screenWidth-screenWidth*0.005;
		this.viewHeight = screenHeight-screenHeight*0.05;
		this.canvas = canvas;
		this.canvas.setWidth(viewWidth);
		this.canvas.setHeight(viewHeight);
	}


	/** Zeichnet das Koordinatensystem */
	public void zeichnen() {
		LOGGER.info("konfiguration={}", this.konfiguration);
		var gc = canvas.getGraphicsContext2D();
		gc.setFill(this.konfiguration.getHintergrundFarbe());
		gc.setStroke(this.konfiguration.getZeichenFarbe());
		gc.setFont(new Font(gc.getFont().getName(), gc.getFont().getSize()*this.konfiguration.getFontFaktor()));
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		gc.setLineWidth(this.konfiguration.getLineWidth());

		// Model X und Model Y
		this.modelWidth = this.konfiguration.getxBis() - this.konfiguration.getxVon();
		this.modelHeight = this.konfiguration.getyBis() - this.konfiguration.getyVon();

		// Umrechnungsfaktor von Punkten aus dem Modell in Punkte der View
		this.faktorXAchse = this.viewWidth / this.modelWidth;
		this.faktorYAchse = this.viewHeight / this.modelHeight;

		LOGGER.info("Bereiche: physisch={}/{}; sichtbar={}/{}; model={}/{}, faktorXAchse={}, faktoryAchse={}",
				screenWidth, screenHeight, viewWidth, viewHeight, modelWidth, modelHeight, faktorXAchse, faktorYAchse);

		/* X-Achse von-bis */
		Point xVon = modelToView(this.konfiguration.getxVon(), 0);
		Point xBis = modelToView(this.konfiguration.getxBis(), 0);
		LOGGER.info("X: {}->{}", xVon, xBis);
		achseZeichnen(gc, "X", xVon, xBis, -10, -10, -10, 10, -30, +20);

		/* Y-Achse von-bis */
		Point yVon = modelToView(0, this.konfiguration.getyVon());
		Point yBis = modelToView(0, this.konfiguration.getyBis());
		LOGGER.info("Y: {}->{}", yVon, yBis);
		achseZeichnen(gc, "Y", yVon, yBis, -10, +10, +10, +10, -20, +30);

//		double X = this.konfiguration.getxVon();
		double X = 0;
//		double Y = Math.sin(Math.toRadians(X));
		double Y = Math.sqrt(X);
		double XNeu = X;
		while ( XNeu < this.konfiguration.getxBis() ) {
			XNeu += 1;
//			double YNeu = Math.sin(Math.toRadians(XNeu));
			double YNeu = Math.sqrt(XNeu);
			Point von = modelToView(X, Y);
			Point bis = modelToView(XNeu, YNeu);
			gc.strokeLine(von.getX(), von.getY(), bis.getX(), bis.getY());
			X = XNeu;
			Y = YNeu;
		}
	}

	/** Zeichnet und beschriftet X- oder Y-Achse */
	private void achseZeichnen(GraphicsContext gc, String achse, Point von, Point bis, int pfeil1X, int pfeil1Y, int pfeil2X, int pfeil2Y, int textX, int textY) {
		LOGGER.info("{}-Achse {} {}", achse, von, bis);
		gc.strokeLine(von.getX(), von.getY(), bis.getX(), bis.getY());
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil1X, bis.getY()+pfeil1Y);
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil2X, bis.getY()+pfeil2Y);
		gc.strokeText(achse, bis.getX()+textX, bis.getY()+textY);
	}

	/** Berechnet, wo ein Punkt aus dem Modell auf dem Bildschirm gezeichnet werden muss */
	public Point modelToView(double x, double y) {
		if ( x < konfiguration.getxVon() || x > konfiguration.getxBis() || y < konfiguration.getyVon() || y > konfiguration.getyBis() )
			throw new IllegalArgumentException("x: "+x+"->"+konfiguration.getxVon()+"/"+konfiguration.getxBis()
				+"; y: "+y+"->"+konfiguration.getyVon()+"/"+konfiguration.getyBis());
		return new Point(
				(int) ((x-this.konfiguration.getxVon())*this.faktorXAchse),
				(int) this.viewHeight - (int) ((y-this.konfiguration.getyVon())*this.faktorYAchse));
	}


	public Konfiguration getKonfiguration() {
		return konfiguration;
	}
	public void setKonfiguration(Konfiguration konfiguration) {
		this.konfiguration = konfiguration;
	}

	/** Speichert die Konfiguration des Koordinatensystems und der Anzeige */
	public static class Konfiguration {
		private double xVon = -500.0, xStart = xVon, xBis = 2_000.0, xEnde = xBis, yVon = -500.0, yBis = 2_000.0, lineWidth = 1.0, fontFaktor = 1.5;
		private Color hintergrundFarbe = Color.BLACK, zeichenFarbe = Color.WHITE;
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
		public Color getHintergrundFarbe() {
			return hintergrundFarbe;
		}
		public void setHintergrundFarbe(Color hintergrundFarbe) {
			this.hintergrundFarbe = hintergrundFarbe;
		}
		public Color getZeichenFarbe() {
			return zeichenFarbe;
		}
		public void setZeichenFarbe(Color zeichenFarbe) {
			this.zeichenFarbe = zeichenFarbe;
		}
		public double getFontFaktor() {
			return fontFaktor;
		}
		public void setFontFaktor(double fontFaktor) {
			this.fontFaktor = fontFaktor;
		}
		public double getxStart() {
			return xStart;
		}
		public void setxStart(double xStart) {
			this.xStart = xStart;
		}
		public double getxEnde() {
			return xEnde;
		}
		public void setxEnde(double xEnde) {
			this.xEnde = xEnde;
		}
		public static class KonfigurationBuilder {
			private Konfiguration konfiguration;
			public KonfigurationBuilder() {
				this.konfiguration = new Konfiguration();
			}
			/** Beginn der X-Achse setzen */
			public KonfigurationBuilder withXVon(double xVon) {
				this.konfiguration.setxVon(xVon);
				return this;
			}
			/** Ab wo auf der X-Achse die Berechnung beginnt */
			public KonfigurationBuilder withXStart(double xStart) {
				this.konfiguration.setxStart(xStart);
				return this;
			}
			/** Ende der X-Achse setzen */
			public KonfigurationBuilder withXBis(double xBis) {
				this.konfiguration.setxBis(xBis);
				return this;
			}
			/** Ab wo auf der X-Achse die Berechnung endet */
			public KonfigurationBuilder withXEnde(double xEnde) {
				this.konfiguration.setxEnde(xEnde);
				return this;
			}
			/** Beginn der Y-Achse setzen */
			public KonfigurationBuilder withYVon(double yVon) {
				this.konfiguration.setyVon(yVon);
				return this;
			}
			/** Ende der Y-Achse setzen */
			public KonfigurationBuilder withYBis(double yBis) {
				this.konfiguration.setyBis(yBis);
				return this;
			}
			/** Hintergrundfarbe des Graphen setzen */
			public KonfigurationBuilder withHintergrundFarbe(Color color) {
				this.konfiguration.setHintergrundFarbe(color);
				return this;
			}
			/** Zeichenfarbe des Graphen und der Beschriftung setzen */
			public KonfigurationBuilder withZeichenFarbe(Color color) {
				this.konfiguration.setZeichenFarbe(color);
				return this;
			}
			/** Faktor für die Vergrößerung der Beschriftung setzen */
			public KonfigurationBuilder withFontFaktor(double fontFaktor) {
				this.konfiguration.setFontFaktor(fontFaktor);
				return this;
			}
			public Konfiguration build() {
				return this.konfiguration;
			}
		}
	}

}
