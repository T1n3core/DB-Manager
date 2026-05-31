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
import io.github.T1n3core.DB_Manager.model.StrojnaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.StrojnaOprema
 * hardware}.
 */
public class StrojnaOpremaDAO implements GenericDAO<StrojnaOprema, Integer> {
	@Override
	public List<StrojnaOprema> findAll() {
		List<StrojnaOprema> strojnaOpremaList = new ArrayList<>();

		String sql = """
				SELECT
					strojna_oprema_id,
					naziv,
					tip,
					serijska_stevilka,
					datum_nakupa
				FROM STROJNA_OPREMA
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				StrojnaOprema strojnaOprema = new StrojnaOprema();

				strojnaOprema.setStrojnaOpremaId(resultSet.getInt("strojna_oprema_id"));
				strojnaOprema.setNaziv(resultSet.getString("naziv"));
				strojnaOprema.setTip(resultSet.getString("tip"));
				strojnaOprema.setSerijskaStevilka(resultSet.getString("serijska_stevilka"));
				strojnaOprema.setDatumNakupa(resultSet.getDate("datum_nakupa").toLocalDate());

				strojnaOpremaList.add(strojnaOprema);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return strojnaOpremaList;
	}

	@Override
	public Optional<StrojnaOprema> findById(Integer id) {
		String sql = """
				SELECT
					strojna_oprema_id,
					naziv,
					tip,
					serijska_stevilka,
					datum_nakupa
				FROM STROJNA_OPREMA
				WHERE strojna_oprema_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					StrojnaOprema strojnaOprema = new StrojnaOprema();

					strojnaOprema.setStrojnaOpremaId(resultSet.getInt("strojna_oprema_id"));
					strojnaOprema.setNaziv(resultSet.getString("naziv"));
					strojnaOprema.setTip(resultSet.getString("tip"));
					strojnaOprema.setSerijskaStevilka(resultSet.getString("serijska_stevilka"));
					strojnaOprema.setDatumNakupa(resultSet.getDate("datum_nakupa").toLocalDate());

					return Optional.of(strojnaOprema);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(StrojnaOprema entity) {
		String sql = """
				INSERT INTO STROJNA_OPREMA
				VALUES (?, ?, ?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, entity.getStrojnaOpremaId());
			statement.setString(2, entity.getNaziv());
			statement.setString(3, entity.getTip());
			statement.setString(4, entity.getSerijskaStevilka());
			statement.setDate(5, Date.valueOf(entity.getDatumNakupa()));

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(StrojnaOprema entity) {
		String sql = """
				UPDATE STROJNA_OPREMA
				SET naziv = ?,
					tip = ?,
					serijska_stevilka = ?,
					datum_nakupa = ?
				WHERE strojna_oprema_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, entity.getNaziv());
			statement.setString(2, entity.getTip());
			statement.setString(3, entity.getSerijskaStevilka());
			statement.setDate(4, Date.valueOf(entity.getDatumNakupa()));
			statement.setInt(5, entity.getStrojnaOpremaId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM STROJNA_OPREMA
				WHERE strojna_oprema_id = ?
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
