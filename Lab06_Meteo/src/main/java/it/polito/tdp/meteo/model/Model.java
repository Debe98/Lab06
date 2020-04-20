package it.polito.tdp.meteo.model;

import java.util.*;

import it.polito.tdp.meteo.DAO.MeteoDAO;


public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private MeteoDAO dao = new MeteoDAO();

	public Model() {

	}

	
	public Map <String, Double> getUmiditaMedia(int mese) {
	
		return dao.getUmiditaLocalitaMese(mese);
	}
	
	
	public Percorso trovaSequenza(int mese) {
		
		List <Rilevamento> rilevamenti = dao.getAllRilevamentiPrimi15GM(mese);
		List <Rilevamento> temp = new LinkedList<Rilevamento>();
		
		Map <String, List<Blocco>> blocchi = new HashMap<String, List<Blocco>>();
		String ultimo = rilevamenti.get(0).getLocalita();
		int cntB = 1;
		
		for (Rilevamento r : rilevamenti) {
			String attuale = r.getLocalita();
			if (!blocchi.containsKey(attuale)) {
				if (!temp.isEmpty()) {
					blocchi.get(ultimo).add(new Blocco(temp, cntB));
				}
				cntB = 1;
				blocchi.put(attuale, new LinkedList<Blocco>());
				ultimo = attuale;
				temp.clear();
			}

			temp.add(r);
			
			if (temp.size() == 3) {
				blocchi.get(attuale).add(new Blocco(temp, cntB));
				temp.clear();
				cntB++;
			}
		}
		
		List <Percorso> best = new LinkedList<Percorso>();
		best.add(null);
		int livello = 1;
		int minCost = -1;

		getCombinazione(1, best, new Percorso(), blocchi);
		return best.get(0);
	}
	
	private void getCombinazione(int livello, List <Percorso> best, Percorso attuale, Map <String, List<Blocco>> blocchi) {
		
		if(livello > 5) {
			if (best.get(0) == null || attuale.getCostoTot()<best.get(0).getCostoTot())
				best.set(0, new Percorso(attuale));
			return;
		}
		
		for (String citta : blocchi.keySet()) {
			if (attuale.getCittaSature().contains(citta))
				continue;
			Blocco daAggiungere = null;
			for (Blocco b : blocchi.get(citta)) {
				if (b.getnBlocco() == livello)
					daAggiungere = b;
			}
			
			Percorso p = new Percorso(attuale);
			p.addBlocco(daAggiungere);
			getCombinazione(livello+1, best, p, blocchi);
		}
		
		return;
	}
	

}
