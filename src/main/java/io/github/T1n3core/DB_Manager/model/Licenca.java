package io.github.T1n3core.DB_Manager.model;

import java.time.LocalDate;

/**
 * Represents a license belonging to a
 * {@linkplain io.github.T1n3core.DB_Manager.model.ProgramskaOprema software
 * entity}.
 * Stores a refference to the entity instead of foreign key integer for easier
 * abstraction.
 */
public class Licenca {
	private int licencaId;
	private String tipLicence;
	private LocalDate datumPoteka;
	private int maxNamestitev;
	private ProgramskaOprema programskaOprema;

	public Licenca() {
	}

	public Licenca(int licencaId, String tipLicence, LocalDate datumPoteka, int maxNamestitev,
			ProgramskaOprema programskaOprema) {
		this.licencaId = licencaId;
		this.tipLicence = tipLicence;
		this.datumPoteka = datumPoteka;
		this.maxNamestitev = maxNamestitev;
		this.programskaOprema = programskaOprema;
	}

	public int getLicencaId() {
		return licencaId;
	}

	public String getTipLicence() {
		return tipLicence;
	}

	public LocalDate getDatumPoteka() {
		return datumPoteka;
	}

	public int getMaxNamestitev() {
		return maxNamestitev;
	}

	public ProgramskaOprema getProgramskaOprema() {
		return programskaOprema;
	}

	public void setLicencaId(int licencaId) {
		this.licencaId = licencaId;
	}

	public void setTipLicence(String tipLicence) {
		this.tipLicence = tipLicence;
	}

	public void setDatumPoteka(LocalDate datumPoteka) {
		this.datumPoteka = datumPoteka;
	}

	public void setMaxNamestitev(int maxNamestitev) {
		this.maxNamestitev = maxNamestitev;
	}

	public void setProgramskaOprema(ProgramskaOprema programskaOprema) {
		this.programskaOprema = programskaOprema;
	}
}
