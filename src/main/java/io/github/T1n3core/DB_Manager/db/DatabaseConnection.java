package io.github.T1n3core.DB_Manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for fetching a connection to the database.
 */
public class DatabaseConnection {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Oracle JDBC driver not found.", e);
		}
	}

	private DatabaseConnection() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated.");
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
	}
}
