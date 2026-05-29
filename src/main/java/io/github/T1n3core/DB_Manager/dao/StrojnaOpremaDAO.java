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

		String sql = "SELECT * FROM STROJNA_OPREMA";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.getResultSet()) {
			while (rs.next()) {
				StrojnaOprema strojnaOprema = new StrojnaOprema();

				strojnaOprema.setStrojnaOpremaId(rs.getInt("strojna_oprema_id"));
				strojnaOprema.setNaziv(rs.getString("naziv"));
				strojnaOprema.setTip(rs.getString("tip"));
				strojnaOprema.setSerijskaStevilka(rs.getString("serijska_stevilka"));
				strojnaOprema.setDatumNakupa(rs.getDate("datum_nakupa").toLocalDate());

				strojnaOpremaList.add(strojnaOprema);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return strojnaOpremaList;
	}

	@Override
	public Optional<StrojnaOprema> findById(Integer id) {
		String sql = "SELECT * FROM STROJNA_OPREMA WHERE strojna_oprema_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					StrojnaOprema strojnaOprema = new StrojnaOprema();

					strojnaOprema.setStrojnaOpremaId(rs.getInt("strojna_oprema_id"));
					strojnaOprema.setNaziv(rs.getString("naziv"));
					strojnaOprema.setTip(rs.getString("tip"));
					strojnaOprema.setSerijskaStevilka(rs.getString("serijska_stevilka"));
					strojnaOprema.setDatumNakupa(rs.getDate("datum_nakupa").toLocalDate());

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
		String sql = "INSERT INTO STROJNA_OPREMA VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, entity.getStrojnaOpremaId());
			stmt.setString(2, entity.getNaziv());
			stmt.setString(3, entity.getTip());
			stmt.setString(4, entity.getSerijskaStevilka());
			stmt.setDate(5, Date.valueOf(entity.getDatumNakupa()));

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(StrojnaOprema entity) {
		String sql = "UPDATE STROJNA_OPREMA SET naziv = ?, tip = ?, serijska_stevilka = ?, datum_nakupa = ? WHERE strojna_oprema_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, entity.getNaziv());
			stmt.setString(2, entity.getTip());
			stmt.setString(3, entity.getSerijskaStevilka());
			stmt.setDate(4, Date.valueOf(entity.getDatumNakupa()));
			stmt.setInt(5, entity.getStrojnaOpremaId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM STROJNA_OPREMA WHERE strojna_oprema_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
