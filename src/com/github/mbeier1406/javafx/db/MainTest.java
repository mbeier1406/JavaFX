package com.github.mbeier1406.javafx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Einfaches jdbc Beispiel. Installieren und Starten der Datenbank (des <a href="https://hub.docker.com/r/gvenzl/oracle-xe">
 * Oracle XE Images</a>). Anlegen der Tabelle mit:
 * <code><pre> 
 * create table test1 ( test number(3) not null );
 * </pre></code>.
 * Ausgabe des Programms: {@code Tabelle: TEST1}.
 * @author mbeier
 *
 */
public class MainTest {

	/** Der Standard Connection-String der DB im Docker Container */
	private static final String CONNECTION  = System.getProperty("conn", "jdbc:oracle:thin:@localhost:1521/xepdb1");

	/** Falls der Benutzer nicht per Property gesetzt wird */
	private static final String USR  = System.getProperty("usr", "benutzer");

	/** Falls das Kennwort nicht per Property gesetzt wird */
	private static final String PASS = System.getProperty("pass", "geheim");

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		try ( Connection c = DriverManager.getConnection(CONNECTION, USR, PASS);
				PreparedStatement s = c.prepareStatement("select tname from tab");
				ResultSet r = s.executeQuery() ) {
			while ( r.next() )
				System.out.println("Tabelle: "+r.getString(1));
		}
	}

}
