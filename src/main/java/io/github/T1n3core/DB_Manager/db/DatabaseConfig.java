package io.github.T1n3core.DB_Manager.db;

/**
 * Utility class holding global constants.
 */
public final class DatabaseConfig {
	public static final String URL = "placeholderurl";
	public static final String USERNAME = "placeholderusername";
	public static final String PASSWORD = "placeholderpassword";

	private DatabaseConfig() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated.");
	}
}
