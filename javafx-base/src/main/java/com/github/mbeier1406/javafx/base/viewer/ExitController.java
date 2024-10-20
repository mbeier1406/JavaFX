package com.github.mbeier1406.javafx.base.viewer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ExitController {

	private ImageView imageView;

    @FXML
    void buttonTapped(ActionEvent event) {
    	final var button = (Button) event.getSource();
    	final var buttonId = button.getId();
    	if ( buttonId.equals("exitButton") )
    		Platform.exit();
    	imageView.setImage(null);
    	((Stage) button.getScene().getWindow()).close();  
    }

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

}
