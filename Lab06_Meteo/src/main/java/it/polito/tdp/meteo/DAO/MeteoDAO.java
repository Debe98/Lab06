package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Rilevamento> getAllRilevamentiPrimi15GM(int mese) {

		final String sql = "SELECT situazione.Localita, situazione.`Data`, situazione.Umidita\r\n" + 
				"FROM situazione\r\n" + 
				"WHERE situazione.`Data`>\"2013-"+mese+"-00\" AND situazione.`Data`<\"2013-"+mese+"-16\";";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


//SAREBBE BELLO; MA NON SAPPIAMO COME USARE LE DATE!
	public Map <String, Double> getUmiditaLocalitaMese(int mese) {

		final String sql = "SELECT situazione.Localita, AVG(situazione.Umidita) as media\r\n" + 
			"FROM situazione\r\n" + 
			"WHERE situazione.`Data`>=\"2013-"+mese+"-01\" AND situazione.`Data`<=\"2013-"+mese+"-31\"\r\n" + 
			"GROUP BY situazione.Localita;";
		//BRUTTO MA NECESSARIO (Fortunatamente INPUT CONTROLLATO!)
		//FORMATO DATA = 2013-10-00 / 2013-11-00
		Map <String, Double> mediaCitta = new HashMap<String, Double>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				mediaCitta.put(rs.getString("Localita"), rs.getDouble("media"));
			}

			conn.close();
			return mediaCitta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
