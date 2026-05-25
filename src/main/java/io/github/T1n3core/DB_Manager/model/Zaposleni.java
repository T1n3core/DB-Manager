package io.github.T1n3core.DB_Manager.model;

/**
 * Represents an employee.
 */
public class Zaposleni {
	private int zaposleniId;
	private String ime;
	private String priimek;
	private String email;
	private String oddelek;

	public Zaposleni() {
	}

	public Zaposleni(int zaposleniId, String ime, String priimek, String email, String oddelek) {
		this.zaposleniId = zaposleniId;
		this.ime = ime;
		this.priimek = priimek;
		this.email = email;
		this.oddelek = oddelek;
	}

	public int getZaposleniId() {
		return zaposleniId;
	}

	public String getIme() {
		return ime;
	}

	public String getPriimek() {
		return priimek;
	}

	public String getEmail() {
		return email;
	}

	public String getOddelek() {
		return oddelek;
	}

	public void setZaposleniId(int zaposleniId) {
		this.zaposleniId = zaposleniId;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOddelek(String oddelek) {
		this.oddelek = oddelek;
	}

	@Override
	public String toString() {
		return ime + " " + priimek;
	}
}
