package io.github.T1n3core.DB_Manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.github.T1n3core.DB_Manager.db.DatabaseConnection;
import io.github.T1n3core.DB_Manager.model.Servis;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Servis services}.
 */
public class ServisDAO implements GenericDAO<Servis, Integer> {
	@Override
	public List<Servis> findAll() {
		List<Servis> servisList = new ArrayList<>();

		String sql = "SELECT * FROM SERVIS";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Servis servis = new Servis();

				servisList.add(servis);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return servisList;
	}

	@Override
	public Optional<Servis> findById(Integer id) {
		return servisi.stream().filter(servis -> servis.getServisId() == id).findFirst();
	}

	@Override
	public void insert(Servis entity) {
		servisi.add(entity);
	}

	@Override
	public void update(Servis entity) {
		Optional<Servis> existingServis = findById(entity.getServisId());

		if (existingServis.isPresent()) {
			Servis servis = existingServis.get();

			servis.setDatumServisa(entity.getDatumServisa());
			servis.setOpis(entity.getOpis());
			servis.setStrojnaOprema(entity.getStrojnaOprema());
		}
	}

	@Override
	public void delete(Integer id) {
		servisi.removeIf(servis -> servis.getServisId() == id);
	}
}
