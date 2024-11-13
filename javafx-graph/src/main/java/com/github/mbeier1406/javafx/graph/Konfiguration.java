package com.github.mbeier1406.javafx.graph;

import java.text.DecimalFormat;

import javafx.scene.paint.Color;

/**
 * Speichert alle Konfigurationsdaten zum Zeichnen des Koordinatensystems
 */
public class Konfiguration {

	/* Standard-Konfiguration definieren */
	private double xVon = -500.0, xStart = xVon, xBis = 2_000.0, xEnde = xBis, xDelta = 1, yVon = -500.0, yBis = 2_000.0, lineWidth = 1.0,
			fontFaktor = 1.5, strichBeschriftungXAchseFaktorX = 0, strichBeschriftungXAchseFaktorY = 12, strichBeschriftungYAchseFaktorX = -8, strichBeschriftungYAchseFaktorY = 12;
	private int anzahlEinheitenStricheXAchse = 10, anzahlEinheitenStricheYAchse = 8;
	private DecimalFormat df = new DecimalFormat("#.##");
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
	public DecimalFormat getDecimalFormat() {
		return df;
	}
	public void setDecimalFormat(String format) {
		this.df = new DecimalFormat(format);
	}
	public double getStrichBeschriftungXAchseFaktorX() {
		return strichBeschriftungXAchseFaktorX;
	}
	public void setStrichBeschriftungXAchseFaktorX(double strichBeschriftungXAchseFaktorX) {
		this.strichBeschriftungXAchseFaktorX = strichBeschriftungXAchseFaktorX;
	}
	public double getStrichBeschriftungXAchseFaktorY() {
		return strichBeschriftungXAchseFaktorY;
	}
	public void setStrichBeschriftungXAchseFaktorY(double strichBeschriftungXAchseFaktorY) {
		this.strichBeschriftungXAchseFaktorY = strichBeschriftungXAchseFaktorY;
	}
	public double getStrichBeschriftungYAchseFaktorX() {
		return strichBeschriftungYAchseFaktorX;
	}
	public void setStrichBeschriftungYAchseFaktorX(double strichBeschriftungYAchseFaktorX) {
		this.strichBeschriftungYAchseFaktorX = strichBeschriftungYAchseFaktorX;
	}
	public double getStrichBeschriftungYAchseFaktorY() {
		return strichBeschriftungYAchseFaktorY;
	}
	public void setStrichBeschriftungYAchseFaktorY(double strichBeschriftungYAchseFaktorY) {
		this.strichBeschriftungYAchseFaktorY = strichBeschriftungYAchseFaktorY;
	}
	public int getAnzahlEinheitenStricheXAchse() {
		return anzahlEinheitenStricheXAchse;
	}
	public void setAnzahlEinheitenStricheXAchse(int anzahlEinheitenStricheXAchse) {
		this.anzahlEinheitenStricheXAchse = anzahlEinheitenStricheXAchse;
	}
	public int getAnzahlEinheitenStricheYAchse() {
		return anzahlEinheitenStricheYAchse;
	}
	public void setAnzahlEinheitenStricheYAchse(int anzahlEinheitenStricheYAchse) {
		this.anzahlEinheitenStricheYAchse = anzahlEinheitenStricheYAchse;
	}
	@Override
	public String toString() {
		return "Konfiguration [xVon=" + xVon + ", xStart=" + xStart + ", xBis=" + xBis + ", xEnde=" + xEnde
				+ ", xDelta=" + xDelta + ", yVon=" + yVon + ", yBis=" + yBis + ", lineWidth=" + lineWidth
				+ ", fontFaktor=" + fontFaktor + ", strichBeschriftungXAchseFaktorX=" + strichBeschriftungXAchseFaktorX
				+ ", strichBeschriftungXAchseFaktorY=" + strichBeschriftungXAchseFaktorY
				+ ", strichBeschriftungYAchseFaktorX=" + strichBeschriftungYAchseFaktorX
				+ ", strichBeschriftungYAchseFaktorY=" + strichBeschriftungYAchseFaktorY
				+ ", anzahlEinheitenStricheXAchse=" + anzahlEinheitenStricheXAchse + ", anzahlEinheitenStricheYAchse="
				+ anzahlEinheitenStricheYAchse + ", df=" + df + ", hintergrundFarbe=" + hintergrundFarbe
				+ ", zeichenFarbe=" + zeichenFarbe + "]";
	}
	public static class KonfigurationBuilder {
		private Konfiguration konfiguration;
		public KonfigurationBuilder(final Konfiguration konfiguration) {
			this.konfiguration = konfiguration;
		}
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
		/** Welchen Wert im Modell auf der X-Achse ein Pixel hat - zum Berechnen der zu zeichnenden Kurve */
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
		/** Linienstärke des Graphen */
		public KonfigurationBuilder withLineWidth(double lineWidth) {
			this.konfiguration.setLineWidth(lineWidth);
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
		/** {@linkplain DecimalFormat} (Nachkommastellen usw.) für die Beschriftung der Achsen setzen */
		public KonfigurationBuilder withDecimalFormat(String format) {
			this.konfiguration.setDecimalFormat(format);
			return this;
		}
		/** Setzt den Faktor, um den die Beschriftung der Einheit auf der X-Achse gegenüber dem Strich nach links/rechts verschoben wird */
		public KonfigurationBuilder withStrichBeschriftungXAchseFaktorX(double strichBeschriftungXAchseFaktorX) {
			this.konfiguration.setStrichBeschriftungXAchseFaktorX(strichBeschriftungXAchseFaktorX);
			return this;
		}
		/** Setzt den Faktor, um den die Beschriftung der Einheit auf der X-Achse gegenüber dem Strich nach oben/unten verschoben wird */
		public KonfigurationBuilder withStrichBeschriftungXAchseFaktorY(double strichBeschriftungXAchseFaktorY) {
			this.konfiguration.setStrichBeschriftungXAchseFaktorY(strichBeschriftungXAchseFaktorY);
			return this;
		}
		/** Setzt den Faktor, um den die Beschriftung der Einheit auf der Y-Achse gegenüber dem Strich nach links/rechts verschoben wird */
		public KonfigurationBuilder withStrichBeschriftungYAchseFaktorX(double strichBeschriftungYAchseFaktorX) {
			this.konfiguration.setStrichBeschriftungYAchseFaktorX(strichBeschriftungYAchseFaktorX);
			return this;
		}
		/** Setzt den Faktor, um den die Beschriftung der Einheit auf der Y-Achse gegenüber dem Strich nach oben/unten verschoben wird */
		public KonfigurationBuilder withStrichBeschriftungYAchseFaktorY(double strichBeschriftungYAchseFaktorY) {
			this.konfiguration.setStrichBeschriftungYAchseFaktorY(strichBeschriftungYAchseFaktorY);
			return this;
		}
		/** Wie viele EinheitenStriche auf der X-Achse gesetzt werdne sollen */
		public KonfigurationBuilder withAnzahlEinheitenStricheXAchse(int anzahlEinheitenStricheXAchse) {
			this.konfiguration.setAnzahlEinheitenStricheXAchse(anzahlEinheitenStricheXAchse);
			return this;
		}
		/** Wie viele EinheitenStriche auf der Y-Achse gesetzt werdne sollen */
		public KonfigurationBuilder withAnzahlEinheitenStricheYAchse(int anzahlEinheitenStricheYAchse) {
			this.konfiguration.setAnzahlEinheitenStricheYAchse(anzahlEinheitenStricheYAchse);
			return this;
		}
		public Konfiguration build() {
			return this.konfiguration;
		}
	}

}
