package com.github.mbeier1406.javafx.db;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;

public class ControllerLogin implements Initializable {

    @FXML
    private Label dbStatusLabel;

    @FXML
    private Circle dbStatusCircle;

    @FXML
    private TextField userNametextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button loginButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginButton.setDisable(true);
	}

    @FXML
    void onKeyReleased(KeyEvent event) {
		loginButton.setDisable(credFail(userNametextField.getText(), 5) || credFail(passwordTextField.getText(), 5));
    }

    private static final boolean credFail(String cred, int len) {
    	return cred.isEmpty()  || cred.trim().isEmpty()  || cred.length() < len;
    }

}
