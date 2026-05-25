package io.github.T1n3core.DB_Manager.model;

/**
 * Represents a contract of
 * {@linkplain io.github.T1n3core.DB_Manager.model.StrojnaOprema hardware} being
 * delegated to an {@linkplain io.github.T1n3core.DB_Manager.model.Zaposleni
 * employee}.
 * Stores entity refferences instead of foreign key integers for easier
 * abstraction.
 */
public class Dodelitev {
	private int dodelitevId;
	private Zaposleni zaposleni;
	private StrojnaOprema strojnaOprema;

	public Dodelitev() {
	}

	public Dodelitev(int dodelitevId, Zaposleni zaposleni, StrojnaOprema strojnaOprema) {
		this.dodelitevId = dodelitevId;
		this.zaposleni = zaposleni;
		this.strojnaOprema = strojnaOprema;
	}

	public int getDodelitevId() {
		return dodelitevId;
	}

	public Zaposleni getZaposleni() {
		return zaposleni;
	}

	public StrojnaOprema getStrojnaOprema() {
		return strojnaOprema;
	}

	public void setDodelitevId(int dodelitevId) {
		this.dodelitevId = dodelitevId;
	}

	public void setZaposleni(Zaposleni zaposleni) {
		this.zaposleni = zaposleni;
	}

	public void setStrojnaOprema(StrojnaOprema strojnaOprema) {
		this.strojnaOprema = strojnaOprema;
	}
}
