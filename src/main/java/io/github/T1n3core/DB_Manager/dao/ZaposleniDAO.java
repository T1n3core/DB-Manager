package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.Zaposleni;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Zaposleni
 * employees}.
 */
public class ZaposleniDAO implements GenericDAO<Zaposleni, Integer> {
	private final List<Zaposleni> zaposleni = new ArrayList<>();

	@Override
	public List<Zaposleni> findAll() {
		return zaposleni;
	}

	@Override
	public Optional<Zaposleni> findById(Integer id) {
		return zaposleni.stream().filter(zaposleni -> zaposleni.getZaposleniId() == id).findFirst();
	}

	@Override
	public void insert(Zaposleni entity) {
		zaposleni.add(entity);
	}

	@Override
	public void update(Zaposleni entity) {
		Optional<Zaposleni> existingZaposleni = findById(entity.getZaposleniId());

		if (existingZaposleni.isPresent()) {
			Zaposleni zaposleni = existingZaposleni.get();

			zaposleni.setIme(entity.getIme());
			zaposleni.setPriimek(entity.getPriimek());
			zaposleni.setEmail(entity.getEmail());
			zaposleni.setOddelek(entity.getOddelek());
		}
	}

	@Override
	public void delete(Integer id) {
		zaposleni.removeIf(zaposleni -> zaposleni.getZaposleniId() == id);
	}
}
