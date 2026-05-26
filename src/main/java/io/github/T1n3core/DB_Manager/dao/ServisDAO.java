package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.Servis;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Servis services}.
 */
public class ServisDAO implements GenericDAO<Servis, Integer> {
	private final List<Servis> servisi = new ArrayList<>();

	@Override
	public List<Servis> findAll() {
		return servisi;
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
