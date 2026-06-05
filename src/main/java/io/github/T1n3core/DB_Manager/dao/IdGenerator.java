package io.github.T1n3core.DB_Manager.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import io.github.T1n3core.DB_Manager.db.DatabaseConnection;

public class IdGenerator {
	public static int nextId(String table, String column) {
		String sql = "SELECT MAX(" + column + ") FROM " + table;

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			if (rs.next()) {
				return rs.getInt(1) + 1;
			}

			return 1;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
