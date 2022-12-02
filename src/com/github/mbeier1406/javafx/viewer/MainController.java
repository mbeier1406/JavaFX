package com.github.mbeier1406.javafx.viewer;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

    private double opacity;
	private SepiaTone sepiaTone;

    @FXML
    public void initialize() {
    	opacity = 0.9;
    	imageView.setEffect(sepiaTone=new SepiaTone(0));
    }

    @FXML
    void openButtonTapped(ActionEvent event) throws IOException {
    	File file;
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
    	imageView.setOpacity((opacity=opacity==0.5?0.9:0.5));
    	System.out.println("opacity="+opacity);
    }

    @FXML
    void effectButtonTapped(ActionEvent event) {
    	sepiaTone.setLevel(sepiaTone.getLevel()==0?0.9:0);
    }

    @FXML
    void endButtonTapped(ActionEvent event) {
    	final var fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(getClass().getResource("ExitFXML.fxml"));
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		((ExitController) fxmlLoader.getController()).setImageView(imageView);
		Scene scene = new Scene(fxmlLoader.getRoot());
		Stage stage = new Stage();
		stage.setTitle("Beenden?");
		stage.setScene(scene);
		stage.show();
    }

}
