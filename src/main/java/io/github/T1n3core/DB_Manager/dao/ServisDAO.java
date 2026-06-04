package io.github.T1n3core.DB_Manager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.db.DatabaseConnection;
import io.github.T1n3core.DB_Manager.model.Servis;
import io.github.T1n3core.DB_Manager.model.StrojnaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Servis services}.
 */
public class ServisDAO implements GenericDAO<Servis, Integer> {
	@Override
	public List<Servis> findAll() {
		List<Servis> servisList = new ArrayList<>();

		String sql = """
				SELECT
					s.servis_id,
					s.datum_servisa,
					s.opis,
					so.strojna_oprema_id,
					so.naziv,
					so.tip,
					so.serijska_stevilka,
					so.datum_nakupa
				FROM SERVIS s
				INNER JOIN STROJNA_OPREMA so
					ON s.strojna_oprema_id = so.strojna_oprema_id
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Servis servis = new Servis();

				servis.setServisId(resultSet.getInt("servis_id"));
				servis.setStrojnaOprema(
						new StrojnaOprema(
								resultSet.getInt("strojna_oprema_id"),
								resultSet.getString("naziv"),
								resultSet.getString("tip"),
								resultSet.getString("serijska_stevilka"),
								resultSet.getDate("datum_nakupa").toLocalDate()));
				servis.setDatumServisa(resultSet.getDate("datum_servisa").toLocalDate());
				servis.setOpis(resultSet.getString("opis"));

				servisList.add(servis);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return servisList;
	}

	@Override
	public Optional<Servis> findById(Integer id) {
		String sql = """
				SELECT
					s.servis_id,
					s.datum_servisa,
					s.opis,
					so.strojna_oprema_id,
					so.naziv,
					so.tip,
					so.serijska_stevilka,
					so.datum_nakupa
				FROM SERVIS s
				INNER JOIN STROJNA_OPREMA so
					ON s.strojna_oprema_id = so.strojna_oprema_id
				WHERE servis_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Servis servis = new Servis();

					servis.setServisId(resultSet.getInt("servis_id"));
					servis.setStrojnaOprema(
							new StrojnaOprema(
									resultSet.getInt("strojna_oprema_id"), resultSet.getString("naziv"),
									resultSet.getString("tip"),
									resultSet.getString("serijska_stevilka"),
									resultSet.getDate("datum_nakupa").toLocalDate()));
					servis.setDatumServisa(resultSet.getDate("datum_servisa").toLocalDate());
					servis.setOpis(resultSet.getString("opis"));

					return Optional.of(servis);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(Servis entity) {
		String sql = """
				INSERT INTO SERVIS
				VALUES (?, ?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int newId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

			statement.setInt(1, newId);
			statement.setInt(2, entity.getStrojnaOprema().getStrojnaOpremaId());
			statement.setDate(3, Date.valueOf(entity.getDatumServisa()));
			statement.setString(4, entity.getOpis());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void update(Servis entity) {
		String sql = """
				UPDATE SERVIS
				SET strojna_oprema_id = ?,
					datum_servisa = ?,
					opis = ?
				WHERE servis_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, entity.getStrojnaOprema().getStrojnaOpremaId());
			statement.setDate(2, Date.valueOf(entity.getDatumServisa()));
			statement.setString(3, entity.getOpis());
			statement.setInt(4, entity.getServisId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM SERVIS
				WHERE servis_id = ?
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
