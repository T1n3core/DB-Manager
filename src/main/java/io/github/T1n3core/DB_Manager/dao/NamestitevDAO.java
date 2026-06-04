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
import io.github.T1n3core.DB_Manager.model.Namestitev;
import io.github.T1n3core.DB_Manager.model.ProgramskaOprema;
import io.github.T1n3core.DB_Manager.model.StrojnaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Namestitev
 * installations}.
 */
public class NamestitevDAO implements GenericDAO<Namestitev, Integer> {
	@Override
	public List<Namestitev> findAll() {
		List<Namestitev> namestitevList = new ArrayList<>();

		String sql = """
				SELECT
					n.namestitev_id,
					n.datum_namestitve,
					so.strojna_oprema_id,
					so.naziv AS so_naziv,
					so.tip,
					so.serijska_stevilka,
					so.datum_nakupa,
					po.programska_oprema_id,
					po.naziv AS po_naziv,
					po.verzija,
					po.proizvajalec
				FROM NAMESTITEV n
				INNER JOIN STROJNA_OPREMA so
					ON n.strojna_oprema_id = so.strojna_oprema_id
				INNER JOIN PROGRAMSKA_OPREMA po
					ON n.programska_oprema_id = po.programska_oprema_id
				 """;

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Namestitev namestitev = new Namestitev();

				namestitev.setNamestitevId(resultSet.getInt("namestitev_id"));
				namestitev.setStrojnaOprema(
						new StrojnaOprema(
								resultSet.getInt("strojna_oprema_id"),
								resultSet.getString("so_naziv"),
								resultSet.getString("tip"),
								resultSet.getString("serijska_stevilka"),
								resultSet.getDate("datum_nakupa").toLocalDate()));
				namestitev.setProgramskaOprema(
						new ProgramskaOprema(
								resultSet.getInt("programska_oprema_id"),
								resultSet.getString("po_naziv"),
								resultSet.getString("verzija"),
								resultSet.getString("proizvajalec")));
				namestitev.setDatumNamestitve(resultSet.getDate("datum_namestitve").toLocalDate());

				namestitevList.add(namestitev);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return namestitevList;
	}

	@Override
	public Optional<Namestitev> findById(Integer id) {
		String sql = """
				SELECT
					n.namestitev_id,
					n.datum_namestitve,
					so.strojna_oprema_id,
					so.naziv AS so_naziv,
					so.tip,
					so.serijska_stevilka,
					so.datum_nakupa,
					po.programska_oprema_id,
					po.naziv AS po_naziv,
					po.verzija,
					po.proizvajalec
				FROM NAMESTITEV n
				INNER JOIN STROJNA_OPREMA so
					ON n.strojna_oprema_id = so.strojna_oprema_id
				INNER JOIN PROGRAMSKA_OPREMA po
					ON n.programska_oprema_id = po.programska_oprema_id
				WHERE namestitev_id = ?
				 """;

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Namestitev namestitev = new Namestitev();

					namestitev.setNamestitevId(resultSet.getInt("namestitev_id"));
					namestitev.setStrojnaOprema(
							new StrojnaOprema(
									resultSet.getInt("strojna_oprema_id"),
									resultSet.getString("so_naziv"),
									resultSet.getString("tip"),
									resultSet.getString("serijska_stevilka"),
									resultSet.getDate("datum_nakupa").toLocalDate()));
					namestitev.setProgramskaOprema(
							new ProgramskaOprema(
									resultSet.getInt("programska_oprema_id"),
									resultSet.getString("po_naziv"),
									resultSet.getString("verzija"),
									resultSet.getString("proizvajalec")));
					namestitev.setDatumNamestitve(resultSet.getDate("datum_namestitve").toLocalDate());

					return Optional.of(namestitev);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(Namestitev entity) {
		String sql = """
				INSERT INTO NAMESTITEV
				VALUES (?, ?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int newId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);

			statement.setInt(1, newId);
			statement.setInt(2, entity.getStrojnaOprema().getStrojnaOpremaId());
			statement.setInt(3, entity.getProgramskaOprema().getProgramskaOpremaId());
			statement.setDate(4, Date.valueOf(entity.getDatumNamestitve()));

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Namestitev entity) {
		String sql = """
				UPDATE NAMESTITEV
				SET strojna_oprema_id = ?,
					programska_oprema_id = ?,
					datum_namestitve = ?
				WHERE namestitev_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, entity.getStrojnaOprema().getStrojnaOpremaId());
			statement.setInt(2, entity.getProgramskaOprema().getProgramskaOpremaId());
			statement.setDate(3, Date.valueOf(entity.getDatumNamestitve()));
			statement.setInt(4, entity.getNamestitevId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM NAMESTITEV
				WHERE namestitev_id = ?
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
