package com.github.mbeier1406.javafx.base.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbBasis {

	/** Der Standard Connection-String der DB im Docker Container */
	private static final String CONNECTION  = System.getProperty("conn", "jdbc:oracle:thin:@localhost:1521/xepdb1");

	/** Falls der Benutzer nicht per Property gesetzt wird */
	private static final String USR  = System.getProperty("usr", "benutzer");

	/** Falls das Kennwort nicht per Property gesetzt wird */
	private static final String PASS = System.getProperty("pass", "geheim");

    /** DB Verbindung */
    protected Connection c;

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch ( ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected void initDb() throws SQLException {
		c = DriverManager.getConnection(CONNECTION, USR, PASS);
		c.setAutoCommit(false);
	}

	public void closeDb() {
    	try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

}
