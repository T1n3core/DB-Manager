package io.github.T1n3core.DB_Manager.model;

import java.time.LocalDate;

/**
 * Represents an installation of
 * {@linkplain io.github.T1n3core.DB_Manager.model.ProgramskaOprema software} on
 * a piece of {@linkplain io.github.T1n3core.DB_Manager.model.StrojnaOprema
 * hardware}.
 * Stores entity refferences instead of foreign key integers for easier
 * abstraction.
 */
public class Namestitev {
	private int namestitevId;
	private LocalDate datumNamestitve;
	private StrojnaOprema strojnaOprema;
	private ProgramskaOprema programskaOprema;

	public Namestitev() {
	}

	public Namestitev(int namestitevId, LocalDate datumNamestitve, StrojnaOprema strojnaOprema,
			ProgramskaOprema programskaOprema) {
		this.namestitevId = namestitevId;
		this.datumNamestitve = datumNamestitve;
		this.strojnaOprema = strojnaOprema;
		this.programskaOprema = programskaOprema;
	}

	public int getNamestitevId() {
		return namestitevId;
	}

	public LocalDate getDatumNamestitve() {
		return datumNamestitve;
	}

	public StrojnaOprema getStrojnaOprema() {
		return strojnaOprema;
	}

	public ProgramskaOprema getProgramskaOprema() {
		return programskaOprema;
	}

	public void setNamestitevId(int namestitevId) {
		this.namestitevId = namestitevId;
	}

	public void setDatumNamestitve(LocalDate datumNamestitve) {
		this.datumNamestitve = datumNamestitve;
	}

	public void setStrojnaOprema(StrojnaOprema strojnaOprema) {
		this.strojnaOprema = strojnaOprema;
	}

	public void setProgramskaOprema(ProgramskaOprema programskaOprema) {
		this.programskaOprema = programskaOprema;
	}
}
