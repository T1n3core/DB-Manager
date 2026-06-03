package io.github.T1n3core.DB_Manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides database connections using runtime credentials.
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
		if (DatabaseConfig.getUsername() == null || DatabaseConfig.getPassword() == null) {
			throw new SQLException("Database credentials not set.");
		}

		return DriverManager.getConnection(
				DatabaseConfig.getUrl(),
				DatabaseConfig.getUsername(),
				DatabaseConfig.getPassword());
	}
}
