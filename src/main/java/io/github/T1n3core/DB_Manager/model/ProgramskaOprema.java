package io.github.T1n3core.DB_Manager.model;

/**
 * Represents a piece of software.
 */
public class ProgramskaOprema {
	private int programskaOpremaId;
	private String naziv;
	private String verzija;
	private String proizvajalec;

	public ProgramskaOprema() {
	}

	public ProgramskaOprema(int programskaOpremaId, String naziv, String verzija, String proizvajalec) {
		this.programskaOpremaId = programskaOpremaId;
		this.naziv = naziv;
		this.verzija = verzija;
		this.proizvajalec = proizvajalec;
	}

	public int getProgramskaOpremaId() {
		return programskaOpremaId;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getVerzija() {
		return verzija;
	}

	public String getProizvajalec() {
		return proizvajalec;
	}

	public void setProgramskaOpremaId(int programskaOpremaId) {
		this.programskaOpremaId = programskaOpremaId;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setVerzija(String verzija) {
		this.verzija = verzija;
	}

	public void setProizvajalec(String proizvajalec) {
		this.proizvajalec = proizvajalec;
	}
}
