package com.github.mbeier1406.javafx.graph;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

/**
 * Zeichnet das Koordinatensystem, auf dem auf Anforderung der Graph abgebildet wird.
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

	/** Zur Beschriftung der Achsen mit Werten */
	final DecimalFormat df = new DecimalFormat("#.##");


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


	/** Ohne angegebene Funktion ein leeres Koordinatensystem zeichnen */
	public void zeichnen() {
		zeichnen(null);
	}

	/**
	 * Zeichnet das Koordinatensystem, und, falls der Parameter nicht <b>null</b> ist,
	 * die entsprechende Kurve zur Funktion. Der daraus resultierende Graph wird und das
	 * Koordinatensystem (X-/Y-Achse, Wertebereich usw.) wird über die {@linkplain #konfiguration}
	 * gesteuert.
	 * @param f die Kurve zum Graphen, wenn <b>null</b>, wird nur das Koordinatensystem gezeichnet
	 */
	public void zeichnen(final Function<Double, Double> f) {
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
		achseZeichnen(
				gc, "X",
				this.konfiguration.getxVon(), 0, this.konfiguration.getxBis(), 0,
				-10, -10, -10, 10,
				-30, +20,
				10, this.konfiguration.getxVon(), this.konfiguration.getxBis());

		/* Y-Achse von-bis */
		achseZeichnen(
				gc, "Y",
				0, this.konfiguration.getyVon(), 0, this.konfiguration.getyBis(),
				-10, +10, +10, +10,
				-20, +30,
				10, this.konfiguration.getyVon(), this.konfiguration.getyBis());

		if ( f != null ) { // wenn eine Funktion gezeichnet werden soll
			double X = this.konfiguration.getxStart();
			double Y = f.apply(X);
			double XNeu = X;
			while ( XNeu+this.konfiguration.getxDelta() < this.konfiguration.getxBis() ) {
				XNeu += this.konfiguration.getxDelta();
				double YNeu = f.apply(XNeu);
				Point von = modelToView(X, Y);
				Point bis = modelToView(XNeu, YNeu);
				gc.strokeLine(von.getX(), von.getY(), bis.getX(), bis.getY());
				X = XNeu;
				Y = YNeu;
			}
		}
	}

	/**
	 * Zeichnet und beschriftet X- oder Y-Achse
	 * @param gc der Zeichenuntergrund
	 * @param achse Beschriftung der Achse
	 * @param vonX X-Position Start der Achse
	 * @param vonY Y-Position Start der Achse
	 * @param bisX X-Position Ende der Achse
	 * @param bisY Y-Position Ende der Achse
	 * @param pfeil1X Teil 1 des Pfeils am Ende der Achse: X-Differenz zu X-Position Ende der Achse
	 * @param pfeil1Y Teil 1 des Pfeils am Ende der Achse: Y-Differenz zu Y-Position Ende der Achse
	 * @param pfeil2X Teil 2 des Pfeils am Ende der Achse: X-Differenz zu X-Position Ende der Achse
	 * @param pfeil2Y Teil 2 des Pfeils am Ende der Achse: Y-Differenz zu Y-Position Ende der Achse
	 * @param textX Beschriftung am Ende der Achse: X-Differenz
	 * @param textY Beschriftung am Ende der Achse: Y-Differenz
	 * @param anzStriche wie viele Striche zur Anzeige der Werte auf der Achse gezeichnet werden sollen
	 * @param stricheVon Startpunkt der Achse (X/Y) zur Berechnung der Distanz zwischen den Strichen
	 * @param stricheBis
	 */
	private void achseZeichnen(
			GraphicsContext gc, String achse,
			double vonX, double vonY,
			double bisX, double bisY,
			int pfeil1X, int pfeil1Y, int pfeil2X, int pfeil2Y, int textX, int textY,
			int anzStriche, double stricheVon, double stricheBis) {
		Point von = modelToView(vonX, vonY);
		Point bis = modelToView(bisX, bisY);
		LOGGER.info("{}-Achse {} {}", achse, von, bis);
		gc.strokeLine(von.getX(), von.getY(), bis.getX(), bis.getY()); // Die X-/Y-Achse zeichnen
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil1X, bis.getY()+pfeil1Y); // Den Pfeil am Ende
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil2X, bis.getY()+pfeil2Y); // der Achse zeichnen
		gc.strokeText(achse, bis.getX()+textX, bis.getY()+textY); // Beschriftung der Achse mit "X" oder "Y"
		int distanzStriche = (int) (stricheBis-stricheVon) / anzStriche; // Distanz der Markierungen auf der Achse
		if ( achse.equals("X") ) {
			for ( double position = distanzStriche; position < bisX; position += distanzStriche ) {
				Point strichVon = modelToView(position, 0);
				Point strichBis = modelToView(position, -0.3);
				gc.strokeLine(strichVon.getX(), strichVon.getY(), strichBis.getX(), strichBis.getY());
				gc.strokeText(df.format(position), strichVon.getX()+3, bis.getY()+20); // Beschriftung des aktuellen Werts auf der Achse
			}
		}
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
		private double xVon = -500.0, xStart = xVon, xBis = 2_000.0, xEnde = xBis, xDelta = 1, yVon = -500.0, yBis = 2_000.0, lineWidth = 1.0, fontFaktor = 1.5;
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
		public double getxDelta() {
			return xDelta;
		}
		public void setxDelta(double xDelta) {
			this.xDelta = xDelta;
		}
		@Override
		public String toString() {
			return "Konfiguration [xVon=" + xVon + ", xStart=" + xStart + ", xBis=" + xBis + ", xEnde=" + xEnde
					+ ", xDelta=" + xDelta + ", yVon=" + yVon + ", yBis=" + yBis + ", lineWidth=" + lineWidth
					+ ", fontFaktor=" + fontFaktor + ", hintergrundFarbe=" + hintergrundFarbe + ", zeichenFarbe="
					+ zeichenFarbe + "]";
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
			/** Welchen Wert im Modell auf der X-Achse ein Pixel hat */
			public KonfigurationBuilder withXDelta(double xDelta) {
				this.konfiguration.setxDelta(xDelta);
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
