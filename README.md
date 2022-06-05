***
<div align="center">
    <b><em>JavaFX</em></b><br>
    Kleine Beispiele zur Demonstration der Nutzung JavaFX
</div>

***

#Voraussetzung

* Die Beispiele sind (teilweise) mit <u>JDK &gt; Version 10</u> erstellt; JavaFX ist nicht mehr Bestandteil des JDK.
* Entwicklung mit Eclipse, über den Marketplace e(fx)clipse installieren (direkt JavaFX-Projekte in Eclipse erstellen).
* <a href="https://gluonhq.com/products/javafx/">SDK herunterladen</a>, z. B. javafx-sdk-17.0.2. und entpacken (z. B. in <code>/home/mbeier/Eclipse/libs/javafx-sdk-17.0.2</code>)
* Aus dem SDK eine User-Library erstellen, indem alle JARs aus dem SDK hinzugefügt werden. Die Bibliothek als User-Library jedem Projekt hinzufügen.
* Um <i>JavaFX Runtime components are missing, ...</i>-Fehler zu vermeiden, siehe:
    * <a href="https://github.com/openjfx/openjfx-docs/issues/91#issuecomment-811525978">Problemlösung auf github</a>
    * <a href="https://openjfx.io/openjfx-docs/">Anleitung JavaFX mit Eclipse für <i>non-modular</i> Projekte</a>: Startparameter <code>--module-path /home/mbeier/Eclipse/libs/javafx-sdk-17.0.2/lib --add-modules=javafx.controls,javafx.fxml</code>
* <a href="https://gluonhq.com/products/scene-builder/">Scene Builder herunterladen/entpacken</a> (z. B. unter <code>/opt/SceneBuilder/SceneBuilder</code>), und in Eclipse unter <i>Windows / Preferences / JavaFX / SceneBuilder executable</i> installieren. Es können jetzt im Projekt <i>*.fxml</i> mit dem SceneBuilder geöffnet und bearbeitet werden.
* <b>Hinweis</b>: für Programme mit <u>JDK &lt; Version 11</u> muss zusätzlich das <i>JavaFX SDK</i> dem Classpath hinzugefügt werden!


#JavaFX Beispiele

* Beipspiel <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html">HBox</a>: Elemente in einer Reihe anordnen
