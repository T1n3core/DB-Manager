package io.github.T1n3core.DB_Manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import io.github.T1n3core.DB_Manager.model.Licenca;

/**
 * Implementation of the
 * {@linkplain io.github.T1n3core.DB_Manager.dao.GenericDAO Generic Data Access
 * Object} for {@linkplain io.github.T1n3core.DB_Manager.model.Licenca
 * licenses}.
 */
public class LicencaDAO implements GenericDAO<Licenca, Integer> {
	private final List<Licenca> licence = new ArrayList<>();

	@Override
	public List<Licenca> findAll() {
		return licence;
	}

	@Override
	public Optional<Licenca> findById(Integer id) {
		return licence.stream().filter(licenca -> licenca.getLicencaId() == id).findFirst();
	}

	@Override
	public void insert(Licenca entity) {
		licence.add(entity);
	}

	@Override
	public void update(Licenca entity) {
		Optional<Licenca> existingLicenca = findById(entity.getLicencaId());

		if (existingLicenca.isPresent()) {
			Licenca licenca = existingLicenca.get();

			licenca.setTipLicence(entity.getTipLicence());
			licenca.setDatumPoteka(entity.getDatumPoteka());
			licenca.setMaxNamestitev(entity.getMaxNamestitev());
			licenca.setProgramskaOprema(entity.getProgramskaOprema());
		}
	}

	@Override
	public void delete(Integer id) {
		licence.removeIf(licenca -> licenca.getLicencaId() == id);
	}
}
