package io.github.T1n3core.DB_Manager.model;

import java.time.LocalDate;

/**
 * Represents a service being done on a piece of
 * {@linkplain io.github.T1n3core.DB_Manager.model.StrojnaOprema hardware}.
 * Stores a refference to the entity instead of foreign key integer for easier
 * abstraction.
 */
public class Servis {
	private int servisId;
	private LocalDate datumServisa;
	private String opis;
	private StrojnaOprema strojnaOprema;

	public Servis() {
	}

	public Servis(int servisId, LocalDate datumServisa, String opis, StrojnaOprema strojnaOprema) {
		this.servisId = servisId;
		this.datumServisa = datumServisa;
		this.opis = opis;
		this.strojnaOprema = strojnaOprema;
	}

	public int getServisId() {
		return servisId;
	}

	public LocalDate getDatumServisa() {
		return datumServisa;
	}

	public String getOpis() {
		return opis;
	}

	public StrojnaOprema getStrojnaOprema() {
		return strojnaOprema;
	}

	public void setServisId(int servisId) {
		this.servisId = servisId;
	}

	public void setDatumServisa(LocalDate datumServisa) {
		this.datumServisa = datumServisa;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setStrojnaOprema(StrojnaOprema strojnaOprema) {
		this.strojnaOprema = strojnaOprema;
	}
}
