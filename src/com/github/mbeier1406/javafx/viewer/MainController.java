package com.github.mbeier1406.javafx.viewer;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * Controller für {@code MainFXML.fxml}.
 * @author mbeier
 *
 */
public class MainController {

    @FXML
    private Button openButton;

    @FXML
    private Button effectButton;

    @FXML
    private Button opacityButton;

    @FXML
    private Button endButton;

    @FXML
    private ImageView imageView;

    /** Die Datei, die angezeigt werden soll */
    private File file;

    @FXML
    void openButtonTapped(ActionEvent event) throws IOException {
    	final var fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter("PNG", "*.png"),
    			new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg"));
    	if ( (file=fileChooser.showOpenDialog(null)) != null )
    		imageView.setImage(new Image(new FileInputStream(requireNonNull(file, "Keine Datei angegeben!"))));
    	imageView.fitWidthProperty().bind(Bindings.selectDouble(imageView.sceneProperty(), "width"));
    	imageView.fitHeightProperty().bind(Bindings.selectDouble(imageView.sceneProperty(), "height"));
    }

    @FXML
    void opacityButtonTapped(ActionEvent event) {

    }

    @FXML
    void effectButtonTapped(ActionEvent event) {

    }

    @FXML
    void endButtonTapped(ActionEvent event) {

    }

}
