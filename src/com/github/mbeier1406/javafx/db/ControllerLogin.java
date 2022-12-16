package com.github.mbeier1406.javafx.db;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;

public class ControllerLogin implements Initializable {

	/** Mindestlaenge User- und Passwortfeld ist {@value} */
    private static final int USR_PASS_LENN = 5;

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

    /** Zum Hashen der Kennwoerter */
    private MessageDigest messageDigest;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		loginButton.setDisable(true);
		Stream.of(userNametextField, passwordTextField).forEach(f -> f.setTooltip(getTooltip(TOOLTIP_MSG_USR_PASS)));
	}

    @FXML
    void onKeyReleased(KeyEvent event) {
		loginButton.setDisable(credFail(userNametextField.getText(), USR_PASS_LENN) || credFail(passwordTextField.getText(), USR_PASS_LENN));
    }

    private static final boolean credFail(String cred, int len) {
    	return cred.isEmpty()  || cred.trim().isEmpty()  || cred.length() < len;
    }

    public Tooltip getTooltip(String msg) {
    	final var tooltip = new Tooltip(msg);
    	tooltip.setGraphicTextGap(0.0);
    	return tooltip;
    }

    /**
     * Hasht das Kennwort. Nur für Testzwecke. Für korrektes Hashing siehe <a
     * href="https://snyk.io/blog/password-hashing-java-applications/">
     * How to do password hashing in Java applications the right way!</a>.
     * @param password das zu hashnde Knnwort
     * @return das gehashte Kennwort
     */
    public String getHasehdPassword(String password) {
		messageDigest.update(password.getBytes());
		return new String(messageDigest.digest());
    }
}
