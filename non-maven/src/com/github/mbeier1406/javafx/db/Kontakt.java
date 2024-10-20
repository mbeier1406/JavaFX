package com.github.mbeier1406.javafx.db;

/**
 * Beinhaltet die Daten eines Kontakts. Die Getter werden für das Laden über Properties in
 * {@value ControllerKontakt#loadContactData(javafx.event.ActionEvent)} benötigt.
 * @author mbeier
 * @see KontaktDAO
 */
public record Kontakt(
		String firstName,
		String lastName,
		String phoneNumber,
		String eMail) {

	public String getFirstName() {
		return firstName();
	}

	public String getLastName() {
		return lastName();
	}

	public String getPhoneNumber() {
		return phoneNumber();
	}

	public String getEMail() {
		return eMail();
	}

}
