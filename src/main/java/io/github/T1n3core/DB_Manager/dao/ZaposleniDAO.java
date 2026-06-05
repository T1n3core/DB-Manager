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

		String sql = """
				SELECT
					zaposleni_id,
					ime,
					priimek,
					email,
					oddelek
				FROM ZAPOSLENI
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Zaposleni zaposleni = new Zaposleni();

				zaposleni.setZaposleniId(resultSet.getInt("zaposleni_id"));
				zaposleni.setIme(resultSet.getString("ime"));
				zaposleni.setPriimek(resultSet.getString("priimek"));
				zaposleni.setEmail(resultSet.getString("email"));
				zaposleni.setOddelek(resultSet.getString("oddelek"));

				zaposleniList.add(zaposleni);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return zaposleniList;
	}

	@Override
	public Optional<Zaposleni> findById(Integer id) {
		String sql = """
				SELECT
					zaposleni_id,
					ime,
					priimek,
					email,
					oddelek
				FROM ZAPOSLENI
				WHERE zaposleni_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Zaposleni zaposleni = new Zaposleni();

					zaposleni.setZaposleniId(resultSet.getInt("zaposleni_id"));
					zaposleni.setIme(resultSet.getString("ime"));
					zaposleni.setPriimek(resultSet.getString("priimek"));
					zaposleni.setEmail(resultSet.getString("email"));
					zaposleni.setOddelek(resultSet.getString("oddelek"));

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
		String sql = """
				INSERT INTO ZAPOSLENI
				VALUES (?, ?, ?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int newId = IdGenerator.nextId("ZAPOSLENI", "zaposleni_id");

			statement.setInt(1, newId);
			statement.setString(2, entity.getIme());
			statement.setString(3, entity.getPriimek());
			statement.setString(4, entity.getEmail());
			statement.setString(5, entity.getOddelek());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Zaposleni entity) {
		String sql = """
				UPDATE ZAPOSLENI
				SET ime = ?,
					priimek = ?,
					email = ?,
					oddelek = ?
				WHERE zaposleni_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, entity.getIme());
			statement.setString(2, entity.getPriimek());
			statement.setString(3, entity.getEmail());
			statement.setString(4, entity.getOddelek());
			statement.setInt(5, entity.getZaposleniId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM ZAPOSLENI
				WHERE zaposleni_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
