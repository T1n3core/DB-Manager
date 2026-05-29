package io.github.T1n3core.DB_Manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.Zaposleni;
import io.github.T1n3core.DB_Manager.db.DatabaseConnection;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Zaposleni
 * employees}.
 */
public class ZaposleniDAO implements GenericDAO<Zaposleni, Integer> {
	@Override
	public List<Zaposleni> findAll() {
		List<Zaposleni> zaposleniList = new ArrayList<>();

		String sql = "SELECT * FROM ZAPOSLENI";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Zaposleni zaposleni = new Zaposleni();

				zaposleni.setZaposleniId(rs.getInt("zaposleni_id"));
				zaposleni.setIme(rs.getString("ime"));
				zaposleni.setPriimek(rs.getString("priimek"));
				zaposleni.setEmail(rs.getString("email"));
				zaposleni.setOddelek(rs.getString("oddelek"));

				zaposleniList.add(zaposleni);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return zaposleniList;
	}

	@Override
	public Optional<Zaposleni> findById(Integer id) {
		String sql = "SELECT * FROM ZAPOSLENI WHERE zaposleni_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Zaposleni zaposleni = new Zaposleni();

					zaposleni.setZaposleniId(rs.getInt("zaposleni_id"));
					zaposleni.setIme(rs.getString("ime"));
					zaposleni.setPriimek(rs.getString("priimek"));
					zaposleni.setEmail(rs.getString("email"));
					zaposleni.setOddelek(rs.getString("oddelek"));

					return Optional.of(zaposleni);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(Zaposleni entity) {
		String sql = "INSERT INTO ZAPOSLENI VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, entity.getZaposleniId());
			stmt.setString(2, entity.getIme());
			stmt.setString(3, entity.getPriimek());
			stmt.setString(4, entity.getEmail());
			stmt.setString(5, entity.getOddelek());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Zaposleni entity) {
		String sql = "UPDATE zaposleni SET ime = ?, priimek = ?, email = ?, oddelek = ? WHERE zaposleni_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, entity.getIme());
			stmt.setString(2, entity.getPriimek());
			stmt.setString(3, entity.getEmail());
			stmt.setString(4, entity.getOddelek());
			stmt.setInt(5, entity.getZaposleniId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM ZAPOSLENI WHERE zaposleni_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
