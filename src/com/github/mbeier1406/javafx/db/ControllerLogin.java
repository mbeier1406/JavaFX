package com.github.mbeier1406.javafx.db;

import static com.github.mbeier1406.javafx.db.MainLogin.FXML_BASE_DIR;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ControllerLogin implements Initializable {

	/** Mindestlaenge User- und Passwortfeld ist {@value} */
    private static final int USR_PASS_LENN = 3;

	/** Tooltip User- und Passwortfeld ist {@value} */
	private static final String TOOLTIP_MSG_USR_PASS = "Bitte "+USR_PASS_LENN+" Zeichen eingeben!";

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

    private LoginDb loginDb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginButton.setDisable(true);
		Stream.of(userNametextField, passwordTextField).forEach(f -> f.setTooltip(getTooltip(TOOLTIP_MSG_USR_PASS)));
		try {
			loginDb = new LoginDb();
			dbStatusCircle.setFill(Color.GREEN);
			dbStatusLabel.setText("Ok");
			displayPopUp("Datenbankverbindung aufgebaut.");
		}
		catch ( LoginException e ) {
			loginDb = null;
			dbStatusCircle.setFill(Color.RED);			
			dbStatusLabel.setText(e.getLocalizedMessage());
			displayPopUp("Datenbankverbindung Fehler!");
		}
	}

    @FXML
    void loginButtonAction(ActionEvent event) {
    	try {
			loginDb.authenticate(userNametextField.getText(), passwordTextField.getText());
			loginDb = null; // DB-Verbindung ist nach erfolgreichem Login geschlossen, Objekt nicht mehr verwenden
			displayPopUp("Login erfolgreich.");
			((Stage) loginButton.getScene().getWindow()).close();
			final var stage = new Stage();
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(FXML_BASE_DIR+"Kontakt.fxml"))));
			stage.setTitle("Dashboard");
			stage.setResizable(false);
			stage.show();
		} catch (LoginException | IOException e) {
			e.printStackTrace();
			displayPopUp("Login nicht erfolgreich!");
		}
    }

    @FXML
    void onKeyReleased(KeyEvent event) {
		loginButton.setDisable(
				loginDb == null ||
				strNotOk(userNametextField.getText(), USR_PASS_LENN) ||
				strNotOk(passwordTextField.getText(), USR_PASS_LENN));
    }

    public static final boolean strNotOk(String cred, int len) {
    	return cred.isEmpty() || cred.trim().isEmpty()  || cred.length() < len;
    }

    public Tooltip getTooltip(String msg) {
    	final var tooltip = new Tooltip(msg);
    	tooltip.setGraphicTextGap(0.0);
    	return tooltip;
    }

    public void displayPopUp(String msg) {
    	final var stage = new Stage();
    	final var label = new Label(msg);
    	final var button = new Button("Ok");
    	button.setOnAction(event -> stage.close());
    	final var vBox = new VBox(10);
    	vBox.setAlignment(Pos.CENTER);
    	vBox.getChildren().addAll(label, button);
    	final var scene = new Scene(vBox, 300, 150);
    	stage.setScene(scene);
    	stage.setTitle("info");
    	stage.showAndWait();
    }

}
