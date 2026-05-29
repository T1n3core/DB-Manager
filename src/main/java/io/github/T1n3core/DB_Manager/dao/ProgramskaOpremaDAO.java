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

		String sql = "SELECT * FROM PROGRAMSKA_OPREMA";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ProgramskaOprema programskaOprema = new ProgramskaOprema();

				programskaOprema.setProgramskaOpremaId(rs.getInt("programska_oprema_id"));
				programskaOprema.setNaziv(rs.getString("naziv"));
				programskaOprema.setVerzija(rs.getString("verzija"));
				programskaOprema.setProizvajalec(rs.getString("proizvajalec"));

				programskaOpremaList.add(programskaOprema);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return programskaOpremaList;
	}

	@Override
	public Optional<ProgramskaOprema> findById(Integer id) {
		String sql = "SELECT * FROM PROGRAMSKA_OPREMA WHERE programska_oprema_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					ProgramskaOprema programskaOprema = new ProgramskaOprema();

					programskaOprema.setProgramskaOpremaId(rs.getInt("programska_oprema_id"));
					programskaOprema.setNaziv(rs.getString("naziv"));
					programskaOprema.setVerzija(rs.getString("verzija"));
					programskaOprema.setProizvajalec(rs.getString("proizvajalec"));

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
		String sql = "INSERT INTO PROGRAMSKA_OPREMA VALUES (?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, entity.getProgramskaOpremaId());
			stmt.setString(2, entity.getNaziv());
			stmt.setString(3, entity.getVerzija());
			stmt.setString(4, entity.getProizvajalec());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(ProgramskaOprema entity) {
		String sql = "UPDATE PROGRAMSKA_OPREMA SET naziv = ?, verzija = ?, proizvajalec = ? WHERE programska_oprema_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, entity.getNaziv());
			stmt.setString(2, entity.getVerzija());
			stmt.setString(3, entity.getProizvajalec());
			stmt.setInt(4, entity.getProgramskaOpremaId());

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM PROGRAMSKA_OPREMA WHERE programska_oprema_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
