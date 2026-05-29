package io.github.T1n3core.DB_Manager.db;

/**
 * Utility class holding global constants.
 */
public final class DatabaseConfig {
	public static final String URL = "jdbc:oracle:thin:@193.2.139.22:1521/ers";
	public static final String USERNAME = "stakult";
	public static final String PASSWORD = "stakult";

	private DatabaseConfig() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated.");
	}
}
