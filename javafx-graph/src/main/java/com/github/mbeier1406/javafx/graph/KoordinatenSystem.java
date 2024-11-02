package com.github.mbeier1406.javafx.graph;

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

	/** Der Untergrund zum Zeichnen */
	private Canvas canvas;

	/** Definiert die Größe der View, die Maße der Anzeige,wird durch den Screen definiert */
	double viewWidth, viewHeight, screenWidth, screenHeight;

	/** Größe des Modells, wird aus der {@linkplain #konfiguration} gelesen */
	double modelWidth, modelHeight;

	/** Umrechnungsfaktor für X und Y Model -> View */
	double faktorXAchse, faktorYAchse;

	/** Zur Beschriftung der Achsen mit Werten */
	final DecimalFormat df = new DecimalFormat("#."+"#".repeat(5));


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
				10, this.konfiguration.getxVon(), this.konfiguration.getxBis(),
				new StrichBerechnung[] {
						new StrichBerechnung((position, bisMax) -> position < bisMax, this.konfiguration.getxBis(), 1),
						new StrichBerechnung((position, bisMin) -> position > bisMin, this.konfiguration.getxVon(), -1)},
				position -> modelToView(position, 0),
				position -> modelToView(position, -0.3),
				3, 20);

		/* Y-Achse von-bis */
		achseZeichnen(
				gc, "Y",
				0, this.konfiguration.getyVon(), 0, this.konfiguration.getyBis(),
				-10, +10, +10, +10,
				-20, +30,
				10, this.konfiguration.getyVon(), this.konfiguration.getyBis(),
				new StrichBerechnung[] {
						new StrichBerechnung((position, bisMax) -> position < bisMax, this.konfiguration.getyBis(), 1),
						new StrichBerechnung((position, bisMin) -> position > bisMin, this.konfiguration.getyVon(), -1)},
				position -> modelToView(0, position),
				position -> modelToView(-1.3, position),
				-15, -3);

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
			int anzStriche, double stricheVon, double stricheBis,
			final StrichBerechnung[] strichBerechnungListe,
			final Function<Double, Point> fStrichVon,
			final Function<Double, Point> fStrichBis,
			double strichBeschriftungDeltaX, double strichBeschriftungDeltaY) {
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
				gc.strokeText(df.format(position), strichBis.getX()+strichBeschriftungDeltaX, strichBis.getY()+strichBeschriftungDeltaY); // Beschriftung des aktuellen Werts auf der Achse
			}
	}
	private record StrichBerechnung(BiFunction<Double, Double, Boolean> f, double bis, int richtung) {};

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
