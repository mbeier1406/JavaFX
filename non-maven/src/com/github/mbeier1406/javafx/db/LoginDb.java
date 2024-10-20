package com.github.mbeier1406.javafx.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

/**
 * Verifiziert ein Login (Userr/Passwort) gegen die konfigurierte Datenbank.
 * @author mbeier
 */
public class LoginDb extends DbBasis {

    /** Zum Hashen der Kennwoerter */
    private static MessageDigest messageDigest;

	static {
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 
	 * @throws LoginException falls die Datenbank nicht geöffnet werden kann
	 */
	public LoginDb() throws LoginException {
		try {
			initDb();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException(e);
		}
	}

	/**
	 * Prüft einen Benutzer mit Passwort gegen die konfigurierte Datenbank.
	 * <b>ACHTUNG</b>: schließt die zugehörige DB-Verbindung nach erfolgreichem Login!
	 * @param usr der Benutzer
	 * @param pass das Kennwort
	 * @throws LoginException falls das Login nicht gültig ist oder die Datenbankabfrage fehlgeschlagen ist
	 */
	public void authenticate(String usr, String pass) throws LoginException {
		if ( c == null ) throw new IllegalArgumentException("Login war bereits erfolgreich! Neues Objekt erzeugen!");
		try ( PreparedStatement s = c.prepareStatement("select password from login where username = ?"); ) {
			s.setString(1,  usr);
			try ( ResultSet r = s.executeQuery(); ) {
				if ( r.next() ) {
					if ( !r.getString(1).equals(getHashedPassword(pass)) )
						throw new LoginException("Falsches Kennwort!");
				}
				else
					throw new LoginException("Unbekannter Benutzer: "+usr);
				closeDb();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException(e);
		}
	}

	/**
     * Hasht das Kennwort. Nur für Testzwecke. Für korrektes Hashing siehe <a
     * href="https://snyk.io/blog/password-hashing-java-applications/">
     * How to do password hashing in Java applications the right way!</a>.
     * @param password das zu hashende Kennwort
     * @return das gehashte Kennwort
     */
    public static String getHashedPassword(String password) {
		messageDigest.update(password.getBytes());
		return Base64.getEncoder().encodeToString(messageDigest.digest());
    }

    /** Zum Test */
    public static final void main(String[] args) throws NoSuchAlgorithmException {
		messageDigest = MessageDigest.getInstance("SHA-256");
    	Arrays
    		.stream(Optional.ofNullable(args).get())
    		.map(LoginDb::getHashedPassword)
    		.forEach(p -> System.out.println("p="+p));
    }

}
