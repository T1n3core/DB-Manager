package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.Namestitev;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Namestitev
 * installations}.
 */
public class NamestitevDAO implements GenericDAO<Namestitev, Integer> {
	private final List<Namestitev> namestitve = new ArrayList<>();

	@Override
	public List<Namestitev> findAll() {
		return namestitve;
	}

	@Override
	public Optional<Namestitev> findById(Integer id) {
		return namestitve.stream().filter(namestitev -> namestitev.getNamestitevId() == id).findFirst();
	}

	@Override
	public void insert(Namestitev entity) {
		namestitve.add(entity);
	}

	@Override
	public void update(Namestitev entity) {
		Optional<Namestitev> existingNamestitev = findById(entity.getNamestitevId());

		if (existingNamestitev.isPresent()) {
			Namestitev namestitev = existingNamestitev.get();

			namestitev.setDatumNamestitve(entity.getDatumNamestitve());
			namestitev.setStrojnaOprema(entity.getStrojnaOprema());
			namestitev.setProgramskaOprema(entity.getProgramskaOprema());
		}
	}

	@Override
	public void delete(Integer id) {
		namestitve.removeIf(namestitev -> namestitev.getNamestitevId() == id);
	}
}
