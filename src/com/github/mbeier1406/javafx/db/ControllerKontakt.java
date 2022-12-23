package com.github.mbeier1406.javafx.db;

import static com.github.mbeier1406.javafx.db.ControllerLogin.strNotOk;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ControllerKontakt implements Initializable {

	/** {@linkplain Kontakt}e aus DB laden und in DB schreiben */
	private KontaktDAO kontaktDAO;

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
    private Circle dbStatusSignal;

    @FXML
    private TableView<Kontakt> contactsTable;

    @FXML
    private TableColumn<Kontakt, String> firstNameCol;

    @FXML
    private TableColumn<Kontakt, String> lastNameCol;

    @FXML
    private TableColumn<Kontakt, String> phoneNumberCol;

    @FXML
    private TableColumn<Kontakt, String> eMailCol;

    @FXML
    void cancelButtonPressed(ActionEvent event) {
    	firstNameTextField.clear();
    	lastNameTextField.clear();
    	phoneNumberTextField.clear();
    	eMailTextField.clear();
    	cancelButton.setDisable(true);
    	createButton.setDisable(true);
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
    	loadContacts();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createButton.setDisable(true);
		cancelButton.setDisable(true);
		try {
			kontaktDAO = new KontaktDAO();
			dbStatusSignal.setFill(Color.GREEN);
			loadContacts();
		} catch (SQLException e) {
			kontaktDAO = null;
			dbStatusSignal.setFill(Color.RED);
		}
	}

	private void loadContacts() {
		if ( kontaktDAO != null ) {
			try {
				ObservableList<Kontakt> kontakte = kontaktDAO.readContacts();
				firstNameCol.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("firstName"));
				lastNameCol.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("lastName"));
				phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("phoneNumber"));
				eMailCol.setCellValueFactory(new PropertyValueFactory<Kontakt, String>("eMail"));
				contactsTable.setItems(kontakte);
				createButton.setDisable(true);
				cancelButton.setDisable(true);
			} catch (KontaktException e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("DB nicht initialisiert!");
	}

}
