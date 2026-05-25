package io.github.T1n3core.DB_Manager.model;

import java.time.LocalDate;

/**
 * Represents a piece of hardware.
 */
public class StrojnaOprema {
	private int strojnaOpremaId;
	private String naziv;
	private String tip;
	private String serijskaStevilka;
	private LocalDate datumNakupa;

	public StrojnaOprema() {
	}

	public StrojnaOprema(int strojnaOpremaId, String naziv, String tip, String serijskaStevilka,
			LocalDate datumNakupa) {
		this.strojnaOpremaId = strojnaOpremaId;
		this.naziv = naziv;
		this.tip = tip;
		this.serijskaStevilka = serijskaStevilka;
		this.datumNakupa = datumNakupa;
	}

	public int getStrojnaOpremaId() {
		return strojnaOpremaId;
	}

	public String getNaziv() {
		return naziv;
	}

	public String getTip() {
		return tip;
	}

	public String getSerijskaStevilka() {
		return serijskaStevilka;
	}

	public LocalDate getDatumNakupa() {
		return datumNakupa;
	}

	public void setStrojnaOpremaId(int strojnaOpremaId) {
		this.strojnaOpremaId = strojnaOpremaId;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public void setSerijskaStevilka(String serijskaStevilka) {
		this.serijskaStevilka = serijskaStevilka;
	}

	public void setDatumNakupa(LocalDate datumNakupa) {
		this.datumNakupa = datumNakupa;
	}
}
