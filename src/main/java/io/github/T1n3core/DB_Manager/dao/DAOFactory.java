package io.github.T1n3core.DB_Manager.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import io.github.T1n3core.DB_Manager.model.*;

/**
 * Utility class that returns a dao for the passed type.
 */
public final class DAOFactory {
	private static final Map<Class<?>, GenericDAO<?, ?>> DAO_MAP = new LinkedHashMap<>();

	static {
		DAO_MAP.put(Zaposleni.class, new ZaposleniDAO());
		DAO_MAP.put(StrojnaOprema.class, new StrojnaOpremaDAO());
		DAO_MAP.put(ProgramskaOprema.class, new ProgramskaOpremaDAO());
		DAO_MAP.put(Servis.class, new ServisDAO());
		DAO_MAP.put(Namestitev.class, new NamestitevDAO());
		DAO_MAP.put(Licenca.class, new LicencaDAO());
		DAO_MAP.put(Dodelitev.class, new DodelitevDAO());
	}

	private DAOFactory() {
		throw new UnsupportedOperationException("Utility class cannot be instantiated.");
	}

	public static GenericDAO<?, ?> getDAO(Class<?> type) {
		return DAO_MAP.get(type);
	}
}
