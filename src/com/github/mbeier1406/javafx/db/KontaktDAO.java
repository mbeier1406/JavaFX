package com.github.mbeier1406.javafx.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Definiert die Funktionen
 * @author mbeier
 * TODO: Interface definieren und korrektes DAO-Objekt erstellen
 */
public class KontaktDAO extends DbBasis {

	public static final String TABLE = "kontakte";

	public static final String SQL = "select firstName, lastName, phoneNumber, eMail from " + TABLE;

	/** Initialisiert die Datenbank */
	public KontaktDAO() throws SQLException {
		initDb();
	}

	public void insertContact() {
		try () {
			
		}
	}

	/**
	 * Liest die Kontakte aus der Datenbank.
	 * @return Liste der Kontakte
	 * @throws KontaktException falls die DB-Abfrage fehlgeschlagen ist
	 */
	public ObservableList<Kontakt> readContacts() throws KontaktException {
		final ObservableList<Kontakt> list = FXCollections.observableArrayList();
		try ( PreparedStatement ps = c.prepareStatement(SQL);
				ResultSet rs = ps.executeQuery() ) {
			while ( rs.next() ) {
				list.add(new Kontakt(
						rs.getString("firstName"),
						rs.getString("lastName"),
						rs.getString("phoneNumber"),
						rs.getString("eMail")));
				System.out.println("Kontakt: "+list.get(list.size()-1));
			}
		} catch (SQLException e) {
			throw new KontaktException(e);
		}
		return list;
	}

	/** DB-Abfrage testen */
	public static final void main(String[] args) throws KontaktException, SQLException {
		System.out.println("Anzahl Kontakte: " + 
			new KontaktDAO()
				.readContacts()
				.stream()
				.peek(System.out::println)
				.count());
	}

}
