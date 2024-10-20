package com.github.mbeier1406.javafx.scenebuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controllerklasse für <code>Application.fxml</code>
 * @author mbeier
 */
public class Controller {

    @FXML
    private Button button1;

    /** Methode für {@linkplain #button1} */
    @FXML
    void button1Action(ActionEvent event) {
    	System.out.println("Test...");
    }

}
