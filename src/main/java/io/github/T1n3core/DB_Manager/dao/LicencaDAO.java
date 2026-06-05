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
import io.github.T1n3core.DB_Manager.model.Licenca;
import io.github.T1n3core.DB_Manager.model.ProgramskaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Licenca
 * licenses}.
 */
public class LicencaDAO implements GenericDAO<Licenca, Integer> {
	@Override
	public List<Licenca> findAll() {
		List<Licenca> licencaList = new ArrayList<>();

		String sql = """
				SELECT
					l.licenca_id,
					l.tip_licence,
					l.datum_poteka,
					l.max_namestitev,
					po.programska_oprema_id,
					po.naziv,
					po.verzija,
					po.proizvajalec
				FROM LICENCA l
				INNER JOIN PROGRAMSKA_OPREMA po
					ON l.programska_oprema_id = po.programska_oprema_id
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Licenca licenca = new Licenca();

				licenca.setLicencaId(resultSet.getInt("licenca_id"));
				licenca.setTipLicence(resultSet.getString("tip_licence"));
				licenca.setDatumPoteka(resultSet.getDate("datum_poteka").toLocalDate());
				licenca.setMaxNamestitev(resultSet.getInt("max_namestitev"));
				licenca.setProgramskaOprema(
						new ProgramskaOprema(
								resultSet.getInt("programska_oprema_id"),
								resultSet.getString("naziv"),
								resultSet.getString("verzija"),
								resultSet.getString("proizvajalec")));

				licencaList.add(licenca);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return licencaList;
	}

	@Override
	public Optional<Licenca> findById(Integer id) {
		String sql = """
				SELECT
					l.licenca_id,
					l.tip_licence,
					l.datum_poteka,
					l.max_namestitev,
					po.programska_oprema_id,
					po.naziv,
					po.verzija,
					po.proizvajalec
				FROM LICENCA l
				INNER JOIN PROGRAMSKA_OPREMA po
					ON l.programska_oprema_id = po.programska_oprema_id
				WHERE l.licenca_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Licenca licenca = new Licenca();

					licenca.setLicencaId(resultSet.getInt("licenca_id"));
					licenca.setTipLicence(resultSet.getString("tip_licence"));
					licenca.setDatumPoteka(resultSet.getDate("datum_poteka").toLocalDate());
					licenca.setMaxNamestitev(resultSet.getInt("max_namestitev"));
					licenca.setProgramskaOprema(
							new ProgramskaOprema(
									resultSet.getInt("programska_oprema_id"),
									resultSet.getString("naziv"),
									resultSet.getString("verzija"),
									resultSet.getString("proizvajalec")));

					return Optional.of(licenca);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(Licenca entity) {
		String sql = """
				INSERT INTO LICENCA
				VALUES (?, ?, ?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int newId = IdGenerator.nextId("LICENCA", "licenca_id");

			statement.setInt(1, newId);
			statement.setString(2, entity.getTipLicence());
			statement.setDate(3, Date.valueOf(entity.getDatumPoteka()));
			statement.setInt(4, entity.getMaxNamestitev());
			statement.setInt(5, entity.getProgramskaOprema().getProgramskaOpremaId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Licenca entity) {
		String sql = """
				UPDATE LICENCA
				SET tip_licence = ?,
					datum_poteka = ?,
					max_namestitev = ?,
					programska_oprema_id = ?
				WHERE licenca_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, entity.getTipLicence());
			statement.setDate(2, Date.valueOf(entity.getDatumPoteka()));
			statement.setInt(3, entity.getMaxNamestitev());
			statement.setInt(4, entity.getProgramskaOprema().getProgramskaOpremaId());
			statement.setInt(5, entity.getLicencaId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM LICENCA
				WHERE licenca_id = ?
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
