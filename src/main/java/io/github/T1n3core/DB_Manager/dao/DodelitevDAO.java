package io.github.T1n3core.DB_Manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.db.DatabaseConnection;
import io.github.T1n3core.DB_Manager.model.Dodelitev;
import io.github.T1n3core.DB_Manager.model.StrojnaOprema;
import io.github.T1n3core.DB_Manager.model.Zaposleni;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Dodelitev
 * delegations}.
 */
public class DodelitevDAO implements GenericDAO<Dodelitev, Integer> {
	@Override
	public List<Dodelitev> findAll() {
		List<Dodelitev> dodelitevList = new ArrayList<>();

		String sql = """
				SELECT
					d.dodelitev_id,
					z.zaposleni_id,
					z.ime,
					z.priimek,
					z.email,
					z.oddelek,
					so.strojna_oprema_id,
					so.naziv,
					so.tip,
					so.serijska_stevilka,
					so.datum_nakupa
				FROM DODELITEV d
				INNER JOIN ZAPOSLENI z
					ON d.zaposleni_id = z.zaposleni_id
				INNER JOIN STROJNA_OPREMA so
					ON d.strojna_oprema_id = so.strojna_oprema_id
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				Dodelitev dodelitev = new Dodelitev();

				dodelitev.setDodelitevId(resultSet.getInt("dodelitev_id"));
				dodelitev.setZaposleni(
						new Zaposleni(
								resultSet.getInt("zaposleni_id"),
								resultSet.getString("ime"),
								resultSet.getString("priimek"),
								resultSet.getString("email"),
								resultSet.getString("oddelek")));
				dodelitev.setStrojnaOprema(
						new StrojnaOprema(
								resultSet.getInt("strojna_oprema_id"),
								resultSet.getString("naziv"),
								resultSet.getString("tip"),
								resultSet.getString("serijska_stevilka"),
								resultSet.getDate("datum_nakupa").toLocalDate()));

				dodelitevList.add(dodelitev);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return dodelitevList;
	}

	@Override
	public Optional<Dodelitev> findById(Integer id) {
		String sql = """
				SELECT
					d.dodelitev_id,
					z.zaposleni_id,
					z.ime,
					z.priimek,
					z.email,
					z.oddelek,
					so.strojna_oprema_id,
					so.naziv,
					so.tip,
					so.serijska_stevilka,
					so.datum_nakupa
				FROM DODELITEV d
				INNER JOIN ZAPOSLENI z
					ON d.zaposleni_id = z.zaposleni_id
				INNER JOIN STROJNA_OPREMA so
					ON d.strojna_oprema_id = so.strojna_oprema_id
				WHERE d.dodelitev_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Dodelitev dodelitev = new Dodelitev();

					dodelitev.setDodelitevId(resultSet.getInt("dodelitev_id"));
					dodelitev.setZaposleni(
							new Zaposleni(
									resultSet.getInt("zaposleni_id"),
									resultSet.getString("ime"),
									resultSet.getString("priimek"),
									resultSet.getString("email"),
									resultSet.getString("oddelek")));
					dodelitev.setStrojnaOprema(
							new StrojnaOprema(
									resultSet.getInt("strojna_oprema_id"),
									resultSet.getString("naziv"),
									resultSet.getString("tip"),
									resultSet.getString("serijska_stevilka"),
									resultSet.getDate("datum_nakupa").toLocalDate()));

					return Optional.of(dodelitev);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return Optional.empty();
	}

	@Override
	public void insert(Dodelitev entity) {
		String sql = """
				INSERT INTO DODELITEV
				VALUES (?, ?, ?)
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			int newId = IdGenerator.nextId("DODELITEV", "dodelitev_id");

			statement.setInt(1, newId);
			statement.setInt(2, entity.getZaposleni().getZaposleniId());
			statement.setInt(3, entity.getStrojnaOprema().getStrojnaOpremaId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Dodelitev entity) {
		String sql = """
				UPDATE DODELITEV
				SET zaposleni_id = ?,
					strojna_oprema_id = ?
				WHERE dodelitev_id = ?
				""";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, entity.getZaposleni().getZaposleniId());
			statement.setInt(2, entity.getStrojnaOprema().getStrojnaOpremaId());
			statement.setInt(3, entity.getDodelitevId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = """
				DELETE FROM DODELITEV
				WHERE dodelitev_id = ?
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
