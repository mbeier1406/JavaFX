package com.github.mbeier1406.javafx.graph;

import static java.util.Objects.requireNonNull;

import java.awt.Point;
import java.text.DecimalFormat;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

	/** liefert den Untergrund zum Zeichnen */
	private Canvas canvas;

	/** Der 2D-Untergrund zum Zeichnen */
	private GraphicsContext gc;

	/** Definiert die Größe der View, die Maße der Anzeige,wird durch den Screen definiert */
	double viewWidth, viewHeight, screenWidth, screenHeight;

	/** Größe des Modells, wird aus der {@linkplain #konfiguration} gelesen */
	double modelWidth, modelHeight;

	/** Umrechnungsfaktor für X und Y Model -> View */
	double faktorXAchse, faktorYAchse;


	/**
	 * Die Beschriftung einer Achse des Koordinatensystems mit Einheiten/Strichen erfordert:
	 * <ul>
	 * <li>Eine Funktion, die das Ende der Berechung abbildet (zB kleiner als das Maximum der Achse)</li>
	 * <li>Angabe des zu prüfenden Wertes für die Funktion (zB das Maximum der Achse)</li>
	 * <li>Das Vorzeichen für die Richtung vom Nullpunkt aus: <b>-1</b> für links/unten, <b>1</b> für rechts/oben</li> 
	 * </ul>
	 */
	private record StrichBerechnung(BiFunction<Double, Double, Boolean> f, double bis, int richtung) {};


	/**	Richtet das Koordinatensystem mit der Standardkonfiguration ein */
	public KoordinatenSystem(final Screen screen, final Canvas canvas) {
		this(screen, canvas, new Konfiguration.KonfigurationBuilder().build());
	}

	/**	Richtet das Koordinatensystem mit einer eigenen {@linkplain Konfiguration} ein */
	public KoordinatenSystem(final Screen screen, final Canvas canvas, final Konfiguration konfiguration) {
		this.konfiguration = requireNonNull(konfiguration, "konfiguration");
		this.screen = requireNonNull(screen, "screen");;
		this.screenWidth = this.screen.getVisualBounds().getWidth();
		this.screenHeight = this.screen.getVisualBounds().getHeight();
		this.viewWidth = screenWidth-screenWidth*0.005;
		this.viewHeight = screenHeight-screenHeight*0.05;
		this.canvas = requireNonNull(canvas, "canvas");
		this.canvas.setWidth(viewWidth);
		this.canvas.setHeight(viewHeight);

		LOGGER.info("konfiguration={}", this.konfiguration);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(this.konfiguration.getHintergrundFarbe());
		gc.setStroke(this.konfiguration.getZeichenFarbe());
		gc.setFont(new Font(gc.getFont().getName(), Font.getDefault().getSize()*this.konfiguration.getFontFaktor()));
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

		/* X-Achse zeichnen */
		achseZeichnen(
				this.gc, "X",
				this.konfiguration.getxVon(), 0, this.konfiguration.getxBis(), 0,
				-10, -10, -10, 10, // Pfelil am Ende der X-Achse
				-30, +20, // Abstand Beschriftung "X" zum Pfeil
				this.konfiguration.getAnzahlEinheitenStricheXAchse(), this.konfiguration.getxVon(), this.konfiguration.getxBis(), // Anzahl Einheitenstriche und Bereich
				new StrichBerechnung[] {
						new StrichBerechnung((position, bisMax) -> position < bisMax, this.konfiguration.getxBis(), 1),
						new StrichBerechnung((position, bisMin) -> position > bisMin, this.konfiguration.getxVon(), -1)},
				position -> modelToView(position, 0), // Einheitenbeschriftung Delta-X zum Strich
				position -> modelToView(position, -this.modelHeight/71), // Einheitenbeschriftung Delta-Y zum Strich
				this.konfiguration.getFontFaktor(), this.konfiguration.getStrichBeschriftungXAchseFaktorX(), this.konfiguration.getStrichBeschriftungXAchseFaktorY(),
				this.konfiguration.getDecimalFormat());

		/* Y-Achse zeichnen */
		achseZeichnen(
				this.gc, "Y",
				0, this.konfiguration.getyVon(), 0, this.konfiguration.getyBis(),
				-10, +10, +10, +10, // Pfelil am Ende der Y-Achse
				-20, +30, // Abstand Beschriftung "Y" zum Pfeil
				this.konfiguration.getAnzahlEinheitenStricheYAchse(), this.konfiguration.getyVon(), this.konfiguration.getyBis(), // Anzahl Einheitenstriche und Bereich
				new StrichBerechnung[] {
						new StrichBerechnung((position, bisMax) -> position < bisMax, this.konfiguration.getyBis(), 1),
						new StrichBerechnung((position, bisMin) -> position > bisMin, this.konfiguration.getyVon(), -1)},
				position -> modelToView(0, position), // Einheitenbeschriftung Delta-X zum Strich
				position -> modelToView(-this.modelWidth/327, position), // Einheitenbeschriftung Delta-Y zum Strich
				this.konfiguration.getFontFaktor(), this.konfiguration.getStrichBeschriftungYAchseFaktorX(), this.konfiguration.getStrichBeschriftungYAchseFaktorY(),
				this.konfiguration.getDecimalFormat());

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
	 * Zeichnet und beschriftet X- oder Y-Achse.
	 * @param gc der Zeichenuntergrund
	 * @param achse Beschriftung der Achse ("<i>X</i>" bzw. "<i>Y</i>")
	 * @param vonX X-Position Start der Achse (Wert aus dem Modell)
	 * @param vonY Y-Position Start der Achse (Wert aus dem Modell)
	 * @param bisX X-Position Ende der Achse (Wert aus dem Modell)
	 * @param bisY Y-Position Ende der Achse (Wert aus dem Modell)
	 * @param pfeil1X Teil 1 des Pfeils am Ende der Achse: X-Differenz zu X-Position Ende der Achse (Wert aus der View/Pixel)
	 * @param pfeil1Y Teil 1 des Pfeils am Ende der Achse: Y-Differenz zu Y-Position Ende der Achse (Wert aus der View/Pixel)
	 * @param pfeil2X Teil 2 des Pfeils am Ende der Achse: X-Differenz zu X-Position Ende der Achse (Wert aus der View/Pixel)
	 * @param pfeil2Y Teil 2 des Pfeils am Ende der Achse: Y-Differenz zu Y-Position Ende der Achse (Wert aus der View/Pixel)
	 * @param textX Beschriftung am Ende der Achse: X-Differenz (Wert aus der View/Pixel)
	 * @param textY Beschriftung am Ende der Achse: Y-Differenz (Wert aus der View/Pixel)
	 * @param anzStriche wie viele Striche zur Anzeige der Werte auf der Achse gezeichnet werden sollen
	 * @param stricheVon Startpunkt der Achse (X/Y) zur Berechnung der Distanz zwischen den Strichen
	 * @param stricheBis Endpunkt der Achse (X/Y) zur Berechnung der Distanz zwischen den Strichen
	 * @param strichBerechnungListe Liste der Funktionen zur Ermittlung des Endes der Striche vom 0-Punkt aus gesehen sowie die Richtung links/rechts bzw. oben unten
	 * @param strichBeschriftungFaktorX Faktor zur Berechnung des Abstands der Beschriftung eines Strichs zum Strich in X-Richtung (Wert aus der View/Pixel)
	 * @param strichBeschriftungFaktorY Faktor zur Berechnung des Abstands der Beschriftung eines Strichs zum Strich in Y-Richtung (Wert aus der View/Pixel)
	 * @param df Das Dezimalformat zur Beschriftung der Striche
	 */
	private void achseZeichnen(
			GraphicsContext gc, String achse,
			double vonX, double vonY,
			double bisX, double bisY,
			int pfeil1X, int pfeil1Y, int pfeil2X, int pfeil2Y, int textX, int textY,
			int anzStriche, double stricheVon, double stricheBis,
			final StrichBerechnung[] strichBerechnungListe,
			final Function<Double, Point> fStrichVon,
			final Function<Double, Point> fStrichBis,
			double fontFaktor, double strichBeschriftungFaktorX, double strichBeschriftungFaktorY,
			final DecimalFormat df) {
		Point von = modelToView(vonX, vonY);
		Point bis = modelToView(bisX, bisY);
		LOGGER.info("{}-Achse {} {}", achse, von, bis);
		gc.strokeLine(von.getX(), von.getY(), bis.getX(), bis.getY()); // Die X-/Y-Achse zeichnen
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil1X, bis.getY()+pfeil1Y); // Den Pfeil am Ende
		gc.strokeLine(bis.getX(), bis.getY(), bis.getX()+pfeil2X, bis.getY()+pfeil2Y); // der Achse zeichnen
		gc.strokeText(achse, bis.getX()+textX, bis.getY()+textY); // Beschriftung der Achse mit "X" oder "Y"
		double distanzStriche = (stricheBis-stricheVon) / anzStriche; // Distanz der Markierungen auf der Achse
		for ( StrichBerechnung strichBerechnung : strichBerechnungListe )
			for ( double position = distanzStriche*strichBerechnung.richtung(); strichBerechnung.f().apply(position, strichBerechnung.bis()); position += distanzStriche*strichBerechnung.richtung() ) {
				Point strichVon = fStrichVon.apply(position);
				Point strichBis = fStrichBis.apply(position);
				gc.strokeLine(strichVon.getX(), strichVon.getY(), strichBis.getX(), strichBis.getY());
				String einheit = df.format(position);
				int deltaX = (int) Math.round(einheit.length()*fontFaktor*strichBeschriftungFaktorX);
				int deltaY = (int) Math.round(fontFaktor*strichBeschriftungFaktorY);
				gc.strokeText(einheit, strichBis.getX()+deltaX, strichBis.getY()+deltaY); // Beschriftung des aktuellen Werts auf der Achse
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

}
