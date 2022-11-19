***
<div align="center">
	<img src="fx_boxback_logo.jpg" width="350" alt="JavaFX">
	<p><b><em>JavaFX</em></b><br>Kleine Beispiele zur Demonstration der Nutzung JavaFX</p>
</div>

***

# Voraussetzung

* Die Beispiele sind (teilweise) mit <u>JDK &gt; Version 10</u> erstellt; JavaFX ist nicht mehr Bestandteil des JDK.
* Entwicklung mit Eclipse, über den Marketplace e(fx)clipse installieren (direkt JavaFX-Projekte in Eclipse erstellen).
* <a href="https://gluonhq.com/products/javafx/">SDK herunterladen</a>, z. B. javafx-sdk-17.0.2. und entpacken (z. B. in <code>/home/mbeier/Eclipse/libs/javafx-sdk-17.0.2</code>)
* Aus dem SDK eine User-Library erstellen, indem alle JARs aus dem SDK hinzugefügt werden. Die Bibliothek als User-Library jedem Projekt hinzufügen.
* Um <i>JavaFX Runtime components are missing, ...</i>-Fehler zu vermeiden, siehe:
    * <a href="https://github.com/openjfx/openjfx-docs/issues/91#issuecomment-811525978">Problemlösung auf github</a>
    * <a href="https://openjfx.io/openjfx-docs/">Anleitung JavaFX mit Eclipse für <i>non-modular</i> Projekte</a>: Startparameter ``--module-path /home/mbeier/Eclipse/libs/javafx-sdk-17.0.2/lib --add-modules=javafx.controls,javafx.fxml``
* <a href="https://gluonhq.com/products/scene-builder/">Scene Builder herunterladen/entpacken</a> (z. B. unter <code>/opt/SceneBuilder/SceneBuilder</code>), und in Eclipse unter <i>Windows / Preferences / JavaFX / SceneBuilder executable</i> installieren. Es können jetzt im Projekt <i>*.fxml</i> mit dem SceneBuilder geöffnet und bearbeitet werden.
* <b>Hinweis</b>: für Programme mit <u>JDK &lt; Version 11</u> muss zusätzlich das <i>JavaFX SDK</i> dem Classpath hinzugefügt werden!


# Weitere Informationen

* CSS-Befehle: <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html">JavaFX CSS Reference Guide</a>
* Farbtabelle: <a href="https://www.farb-tabelle.de/de/farbtabelle.htm">Farbtafel mit Beispielen</a>
* Eclipse Launch Configuration: <a href="https://github.com/mbeier1406/JavaFX/blob/master/src/com/github/mbeier1406/javafx/quizapp/Main.launch">Ein Beispiel für eine Startkonfiguration</a>

# JavaFX Beispiele

Beim Erzeugen eines <i>executable JAR</i> in Eclipse über <code>File -> Export -> Java Runnable JAR file</code> werden die VM-Parameter nicht berücksichtigt,
d. h. man benötigt ein Shellscript, in dem die Parameter beim Java-Aufruf mitgegeben werden (Startparameter, ab Java 11 erforderlich).

|Beispiel                                         |Oracle Dokumentation                                                                                | Beispiel                                                    |
| :---------------------------------------------: | :------------------------------------------------------------------------------------------------: | :---------------------------------------------------------: |
|Elemente in einer Reihe anordnen ✓               |<a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html">HBox</a>        |<code>com.github.mbeier1406.javafx.hbox.Main</code>          |
|Texte und Größe von Objekten automatisch ändern ✓|<a href="https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm">Listenern und Binding</a>     |<code>com.github.mbeier1406.javafx.binding.Main.java</code>  |
|Auf Events reagieren ✓                           |<a href="https://docs.oracle.com/javafx/2/events/handlers.htm">Event Handlers</a>                   |<code>com.github.mbeier1406.javafx.events.Main.java</code>   |
|Controls: Buttons, Labels, Textfelder, ... ✓     |<a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Control.html">Controls</a>|<code>com.github.mbeier1406.javafx.controls.Main*.java</code>|
|Views: ListView und TableView ✓                  |<a href="https://docs.oracle.com/javafx/2/ui_controls/table-view.htm">TableView</a>                 |<code>com.github.mbeier1406.javafx.view.Main.java</code>     |
|2D-Grafiken                                      |<a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/shape/Shape.html">Class Shape</a> |<code>com.github.mbeier1406.javafx.graphic.Main*.java</code> |
|Dock: Beispiel für ein Programmschnellstart      |                                                                                                    |<code>com.github.mbeier1406.javafx.dock.Main.java</code>     |

