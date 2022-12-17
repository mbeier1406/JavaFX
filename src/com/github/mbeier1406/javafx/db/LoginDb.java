package com.github.mbeier1406.javafx.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class LoginDb {

	/** Der Standard Connection-String der DB im Docker Container */
	private static final String CONNECTION  = System.getProperty("conn", "jdbc:oracle:thin:@localhost:1521/xepdb1");

	/** Falls der Benutzer nicht per Property gesetzt wird */
	private static final String USR  = System.getProperty("usr", "benutzer");

	/** Falls das Kennwort nicht per Property gesetzt wird */
	private static final String PASS = System.getProperty("pass", "geheim");

    /** Zum Hashen der Kennwoerter */
    private static MessageDigest messageDigest;

    /** DB Verbindung */
    private Connection c;

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException | ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 
	 * @throws LoginException falls die Datenbank nicht geöffnet werden kann
	 */
	public LoginDb() throws LoginException {
		try {
			c = DriverManager.getConnection(CONNECTION, USR, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new LoginException(e);
		}
	}

	public void authenticate(String usr, String pass) throws LoginException {
		try ( PreparedStatement s = c.prepareStatement("select password from login where username = ?"); ) {
			s.setString(1,  usr);
			try ( ResultSet r = s.executeQuery(); ) {
				if ( r.next() ) {
					if ( !r.getString(1).equals(getHashedPassword(pass)) )
						throw new LoginException("Falsches Kennwort!");
				}
				else
					throw new LoginException("Unbekannter Benutzer: "+usr);
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

    public void closeDb() {
    	try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public static final void main(String[] args) throws NoSuchAlgorithmException {
		messageDigest = MessageDigest.getInstance("SHA-256");
    	Arrays
    		.stream(Optional.ofNullable(args).get())
    		.map(LoginDb::getHashedPassword)
    		.forEach(p -> System.out.println("p="+p));
    }

}
