package io.github.T1n3core.DB_Manager.db;

/**
 * Runtime database configuration holder.
 */
public final class DatabaseConfig {
	private static final String URL = "jdbc:oracle:thin:@193.2.139.22:1521/ers";
	private static String username;
	private static String password;

	private DatabaseConfig() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated.");
	}

	public static void setCredentials(String user, char[] pass) {
		username = user;
		password = new String(pass);
	}

	public static String getUrl() {
		return URL;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}

	public static void clearCredentials() {
		username = null;
		password = null;
	}
}
