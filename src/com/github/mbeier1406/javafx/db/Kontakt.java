package com.github.mbeier1406.javafx.db;

import javafx.beans.property.SimpleStringProperty;

public record Kontakt(
		SimpleStringProperty firstName,
		SimpleStringProperty lastName,
		SimpleStringProperty phoneNumber,
		SimpleStringProperty eMail) {

	public Kontakt(String firstName, String lastName, String phoneNumber, String eMail) {
		this(
			new SimpleStringProperty(firstName),
			new SimpleStringProperty(lastName),
			new SimpleStringProperty(phoneNumber),
			new SimpleStringProperty(eMail));
	}

}
