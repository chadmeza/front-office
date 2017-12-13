package com.riteboiler.frontoffice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class connects to the database.
 * It is used by a database helper class.
 * @author Chad Meza
 */
public class RiteDB {
	private final String driver = "org.sqlite.JDBC";
	private final String database = "sqlite";
	private final String fileName = "C:\\Users\\Chad Meza\\Downloads\\frontoffice.db";
	private Connection connection = null;
	
	/**
	 * Establishes a connection to the database.
	 * @return Connection to the database
	 */
	public Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection("jdbc:" + database + ":" + fileName);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
}
