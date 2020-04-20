package it.polito.tdp.meteo.model;

import java.util.*;

public class Percorso {

	private int livello;
	private List <Blocco> blocchi = new LinkedList<Blocco>();
	private int costoTot;
	private Map <String, Integer> citta = new HashMap<>();
	private Set <String> cittaSature = new HashSet<>();
	
	public Percorso() {
		super();
		livello = 0;
		costoTot = 0;
	}
	
	public Percorso(Blocco b) {
		super();
		livello = 1;
		blocchi.add(b);
		costoTot = b.getUmiditaTot();
		citta.put(b.getLocalità(), 1);
	}
	
	public Percorso (Percorso p) {
		livello = p.livello;
		costoTot = p.costoTot;
		for (Blocco b : p.blocchi)
			blocchi.add(b);
		citta = new HashMap<String, Integer> (p.citta);
		cittaSature = new HashSet<String> (p.cittaSature);
	}
	
	public int getLivello() {
		return livello;
	}
	
	public List<Blocco> getBlocchi() {
		return blocchi;
	}
	
	public int getCostoTot() {
		return costoTot;
	}
	
	public void addBlocco (Blocco b) {
		if (citta.containsKey(b.getLocalità())) {
			citta.replace(b.getLocalità(), citta.get(b.getLocalità())+1);
			cittaSature.add(b.getLocalità());
		}
		else {
			citta.put(b.getLocalità(), 1);
		}
		if (livello > 0) {
			//System.out.println(blocchi.size()+" "+livello);
			if (!blocchi.get(blocchi.size()-1).getLocalità().equals(b.getLocalità()))
				costoTot += 100;
		}
		blocchi.add(b);
		costoTot+= b.getUmiditaTot();
		livello = blocchi.size();
	}
	
	public Set<String> getCittaSature() {
		return cittaSature;
	}

	@Override
	public String toString() {
		String s = "Percorso:\n";
		for (Blocco b : blocchi)
			s+= b.getLocalità() + " ("+ b.getUmiditaTot()+")\n";
		return s + "Il costo totale e' " + costoTot +"€";
	}
	
	
}
