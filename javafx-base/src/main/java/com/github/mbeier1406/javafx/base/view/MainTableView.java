package com.github.mbeier1406.javafx.base.view;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Demonstriert die Benutzung von {@linkplain TableView}.
 * @author mbeier
 */
public class MainTableView extends Application {

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {

		final var label = new Label("Adressen");
		label.setFont(new Font(20));

		final var tableView =  new TableView<Person>();
		final var emailNameCol = new TableColumn<Person, String>("E-Mail");
		emailNameCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		emailNameCol.setMinWidth(200);
		emailNameCol.setMaxWidth(250);
		emailNameCol.setSortable(true);
		emailNameCol.setSortType(SortType.ASCENDING);
		emailNameCol.setCellFactory(TextFieldTableCell.<Person>forTableColumn());
		emailNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person,String>>() {	
			@Override
			public void handle(CellEditEvent<Person, String> event) {
				tableView.getItems().get(event.getTablePosition().getRow()).setEmail(event.getNewValue());
			}
		});
		final var userNameCol = new TableColumn<Person, String>("Username");
		userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
		userNameCol.setMinWidth(100);
		userNameCol.setMaxWidth(150);
		final var firstNameCol = new TableColumn<Person, String>("Vorname");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		final var lastNameCol = new TableColumn<Person, String>("Nachname");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		final var fullNameCol = new TableColumn<Person, String>("Name");
		fullNameCol.getColumns().addAll(firstNameCol, lastNameCol);
		tableView.getColumns().addAll(userNameCol, fullNameCol, emailNameCol);
		tableView.setItems(getPersonen());
		tableView.setEditable(true);

		final var vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10, 0, 0, 10));
		vBox.getChildren().addAll(label, tableView);

		final var group = new Group();
		group.getChildren().addAll(vBox);

		final var scene = new Scene(group, 550, 450);
		stage.setScene(scene);
		stage.show();
	}

	public static final void main(String[] args) {
		launch(args);
	}

	/** Liefert eine Beispielliste für {@linkplain Person} */
	public ObservableList<Person> getPersonen() {
		return FXCollections.observableArrayList(
				new Person("mbeier", "Martin", "Beier", "Martin.Beier@gmx.de"),
				new Person("kwolters", "Kulle", "Wolters", "Kulle.Wolter@gmx.de"),
				new Person("pmueller", "Peter", "Müller", "Peter.Mueller@gmx.de"),
				new Person("sschulz", "Sandra", "Schulz", "Sandra.Schulz@gmx.de"),
				new Person("cbach", "Claude", "Bachmann", "cbach@gmx.de"),
				new Person("gossi", "Grit", "Gossmann", "gosi@gmx.de"));
	}

	/**
	 * Beispielobjekt zur Darstellung in der {@linkplain TableView}.
	 * @author mbeier
	 */
	public class Person {

		private final SimpleStringProperty userName;
		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty email;

		public Person(String uName, String fName, String lName, String email) {
			this.userName = new SimpleStringProperty(uName);
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
		}

		public String getFirstName() {
			return firstName.get();
		}
		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public void setUserName(String uName) {
			userName.set(uName);
		}
		public String getLastName() {
			return lastName.get();
		}
		public void setLastName(String fName) {
			lastName.set(fName);
		}
		public String getEmail() {
			return email.get();
		}
		public void setEmail(String fName) {
			email.set(fName);
		}
		public String getUserName() {
			return userName.get();
		}

		@Override
		public String toString() {
			return "Person [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
					+ email + "]";
		}

	}

}
