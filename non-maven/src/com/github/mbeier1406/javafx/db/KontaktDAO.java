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

	/** Die Datenbanktabelle, in der die Kotakte gespeichert werden ist {@value} */
	public static final String TABLE = "kontakte";

	/** Die Abfrage zum Laden der Kontakte: {@value} */
	public static final String SQL_QUERY = "select firstName, lastName, phoneNumber, eMail from " + TABLE;

	/** Das SQL zum Speichern der Kontakte: {@value} */
	public static final String SQL_INSERT = "insert into "+TABLE+" ( firstName, lastName, phoneNumber, eMail ) values ( ?, ?, ?, ? )";

	/** Initialisiert die Datenbank */
	public KontaktDAO() throws SQLException {
		initDb();
	}

	/** Einen Kontakt in die Datenbank einfügen */
	public void insertContact(Kontakt kontakt) throws KontaktException {
		try ( PreparedStatement ps = c.prepareStatement(SQL_INSERT) ) {
			ps.setString(1, kontakt.firstName());
			ps.setString(2, kontakt.lastName());
			ps.setString(3, kontakt.phoneNumber());
			ps.setString(4, kontakt.eMail());
			ps.execute();
			c.commit();
			
		} catch (SQLException e) {
			throw new KontaktException(e);
		}
	}

	/**
	 * Liest die Kontakte aus der Datenbank.
	 * @return Liste der Kontakte
	 * @throws KontaktException falls die DB-Abfrage fehlgeschlagen ist
	 */
	public ObservableList<Kontakt> readContacts() throws KontaktException {
		final ObservableList<Kontakt> list = FXCollections.observableArrayList();
		try ( PreparedStatement ps = c.prepareStatement(SQL_QUERY);
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
