package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.Dodelitev;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Dodelitev
 * delegations}.
 */
public class DodelitevDAO implements GenericDAO<Dodelitev, Integer> {
	private final List<Dodelitev> dodelitve = new ArrayList<>();

	@Override
	public List<Dodelitev> findAll() {
		return dodelitve;
	}

	@Override
	public Optional<Dodelitev> findById(Integer id) {
		return dodelitve.stream().filter(dodelitev -> dodelitev.getDodelitevId() == id).findFirst();
	}

	@Override
	public void insert(Dodelitev entity) {
		dodelitve.add(entity);
	}

	@Override
	public void update(Dodelitev entity) {
		Optional<Dodelitev> existingDodelitev = findById(entity.getDodelitevId());

		if (existingDodelitev.isPresent()) {
			Dodelitev dodelitev = existingDodelitev.get();

			dodelitev.setZaposleni(entity.getZaposleni());
			dodelitev.setStrojnaOprema(entity.getStrojnaOprema());
		}
	}

	@Override
	public void delete(Integer id) {
		dodelitve.removeIf(dodelitev -> dodelitev.getDodelitevId() == id);
	}
}
