package it.polito.tdp.meteo.model;

import java.util.*;

public class Blocco {
	private int nBlocco;
	private int umiditaTot;
	private String località;
	
	public Blocco(List <Rilevamento> a, int nBlocco) {
		super();
		località = a.get(0).getLocalita();
		umiditaTot = 0;
		for (Rilevamento r : a) {
			umiditaTot+= r.getUmidita();
		}
		this.nBlocco = nBlocco;
	}

	public int getnBlocco() {
		return nBlocco;
	}

	public int getUmiditaTot() {
		return umiditaTot;
	}

	public String getLocalità() {
		return località;
	}
	
	
	
	
}
