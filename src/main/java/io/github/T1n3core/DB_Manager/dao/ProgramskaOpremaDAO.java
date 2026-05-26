package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.ProgramskaOprema;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.ProgramskaOprema
 * software}.
 */
public class ProgramskaOpremaDAO implements GenericDAO<ProgramskaOprema, Integer> {
	private final List<ProgramskaOprema> programskaOprema = new ArrayList<>();

	@Override
	public List<ProgramskaOprema> findAll() {
		return programskaOprema;
	}

	@Override
	public Optional<ProgramskaOprema> findById(Integer id) {
		return programskaOprema.stream().filter(programskaOprema -> programskaOprema.getProgramskaOpremaId() == id)
				.findFirst();
	}

	@Override
	public void insert(ProgramskaOprema entity) {
		programskaOprema.add(entity);
	}

	@Override
	public void update(ProgramskaOprema entity) {
		Optional<ProgramskaOprema> existingProgramskaOprema = findById(entity.getProgramskaOpremaId());

		if (existingProgramskaOprema.isPresent()) {
			ProgramskaOprema programskaOprema = existingProgramskaOprema.get();

			programskaOprema.setNaziv(entity.getNaziv());
			programskaOprema.setProizvajalec(entity.getProizvajalec());
			programskaOprema.setVerzija(entity.getVerzija());
		}
	}

	@Override
	public void delete(Integer id) {
		programskaOprema.removeIf(programskaOprema -> programskaOprema.getProgramskaOpremaId() == id);
	}
}
