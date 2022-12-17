package com.github.mbeier1406.javafx.db;

import static com.github.mbeier1406.javafx.db.ControllerLogin.strNotOk;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ControllerKontakt implements Initializable {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField eMailTextField;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    void cancelButtonPressed(ActionEvent event) {

    }

    @FXML
    void createButtonPressed(ActionEvent event) {

    }

    @FXML
    void eMailTextField(ActionEvent event) {

    }

    @FXML
    void keyReleasedProperty(KeyEvent event) {
    	final var fStr = firstNameTextField.getText();
    	final var lStr = lastNameTextField.getText();
    	final var pStr = phoneNumberTextField.getText();
    	final var eStr = eMailTextField.getText();
    	cancelButton.setDisable(
    			strNotOk(fStr, 3) &&
    			strNotOk(lStr, 3) &&
    			strNotOk(pStr, 3) &&
    			strNotOk(eStr, 3) );
    	createButton.setDisable(
    			strNotOk(fStr, 3) ||
    			strNotOk(lStr, 3) ||
    			strNotOk(pStr, 3) ||
    			strNotOk(eStr, 3) );  
    }

    @FXML
    void loadContactData(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createButton.setDisable(true);
		cancelButton.setDisable(true);
		
	}

}
