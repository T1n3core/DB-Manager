package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.StrojnaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.StrojnaOprema
 * hardware}.
 */
public class StrojnaOpremaDAO implements GenericDAO<StrojnaOprema, Integer> {
	private final List<StrojnaOprema> strojnaOprema = new ArrayList<>();

	@Override
	public List<StrojnaOprema> findAll() {
		return strojnaOprema;
	}

	@Override
	public Optional<StrojnaOprema> findById(Integer id) {
		return strojnaOprema.stream().filter(strojnaOprema -> strojnaOprema.getStrojnaOpremaId() == id).findFirst();
	}

	@Override
	public void insert(StrojnaOprema entity) {
		strojnaOprema.add(entity);
	}

	@Override
	public void update(StrojnaOprema entity) {
		Optional<StrojnaOprema> existingStrojnaOprema = findById(entity.getStrojnaOpremaId());

		if (existingStrojnaOprema.isPresent()) {
			StrojnaOprema strojnaOprema = existingStrojnaOprema.get();

			strojnaOprema.setNaziv(entity.getNaziv());
			strojnaOprema.setTip(entity.getTip());
			strojnaOprema.setSerijskaStevilka(entity.getSerijskaStevilka());
			strojnaOprema.setDatumNakupa(entity.getDatumNakupa());
		}
	}

	@Override
	public void delete(Integer id) {
		strojnaOprema.removeIf(strojnaOprema -> strojnaOprema.getStrojnaOpremaId() == id);
	}
}
