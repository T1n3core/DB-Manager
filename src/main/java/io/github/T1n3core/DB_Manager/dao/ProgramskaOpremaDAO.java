package io.github.T1n3core.DB_Manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.db.DatabaseConnection;
import io.github.T1n3core.DB_Manager.model.ProgramskaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.ProgramskaOprema
 * software}.
 */
public class ProgramskaOpremaDAO implements GenericDAO<ProgramskaOprema, Integer> {
	@Override
	public List<ProgramskaOprema> findAll() {
		List<ProgramskaOprema> programskaOpremaList = new ArrayList<>();

		String sql = """
				SELECT
					programska_oprema_id,
					naziv,
					verzija,
					proizvajalec
				FROM PROGRAMSKA_OPREMA
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				ProgramskaOprema programskaOprema = new ProgramskaOprema();

				programskaOprema.setProgramskaOpremaId(resultSet.getInt("programska_oprema_id"));
				programskaOprema.setNaziv(resultSet.getString("naziv"));
				programskaOprema.setVerzija(resultSet.getString("verzija"));
				programskaOprema.setProizvajalec(resultSet.getString("proizvajalec"));

				programskaOpremaList.add(programskaOprema);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return programskaOpremaList;
	}

	@Override
	public Optional<ProgramskaOprema> findById(Integer id) {
		String sql = """
				SELECT
					programska_oprema_id,
					naziv,
					verzija,
					proizvajalec
				FROM PROGRAMSKA_OPREMA
				WHERE programska_oprema_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					ProgramskaOprema programskaOprema = new ProgramskaOprema();

					programskaOprema.setProgramskaOpremaId(resultSet.getInt("programska_oprema_id"));
					programskaOprema.setNaziv(resultSet.getString("naziv"));
					programskaOprema.setVerzija(resultSet.getString("verzija"));
					programskaOprema.setProizvajalec(resultSet.getString("proizvajalec"));

					return Optional.of(programskaOprema);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(ProgramskaOprema entity) {
		String sql = """
				INSERT INTO PROGRAMSKA_OPREMA
				VALUES (?, ?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int newId = IdGenerator.nextId("PROGRAMSKA_OPREMA", "programska_oprema_id");

			statement.setInt(1, newId);
			statement.setString(2, entity.getNaziv());
			statement.setString(3, entity.getVerzija());
			statement.setString(4, entity.getProizvajalec());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(ProgramskaOprema entity) {
		String sql = """
				UPDATE PROGRAMSKA_OPREMA
				SET naziv = ?,
					verzija = ?,
					proizvajalec = ?
				WHERE programska_oprema_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, entity.getNaziv());
			statement.setString(2, entity.getVerzija());
			statement.setString(3, entity.getProizvajalec());
			statement.setInt(4, entity.getProgramskaOpremaId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM PROGRAMSKA_OPREMA
				WHERE programska_oprema_id = ?
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
