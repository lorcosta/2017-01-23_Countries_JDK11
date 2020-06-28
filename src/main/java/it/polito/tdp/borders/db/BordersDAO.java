package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries(Map<String,Country> idMapCountry) {
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet rs = st.executeQuery() ;
			List<Country> list = new LinkedList<Country>() ;
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				if(!idMapCountry.containsKey(c.getStateAbb())) {
					idMapCountry.put(c.getStateAbb(), c);
				}
				list.add(c) ;
			}
			conn.close() ;
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}

	public List<Adiacenza> listAdiacenze(Integer anno,Map<String,Country> idMapCountry) {
		String sql="SELECT * " + 
				"FROM contiguity " + 
				"WHERE year<=? AND state1ab>state2ab AND conttype=1";
		List<Adiacenza> list = new LinkedList<Adiacenza>() ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
			while( rs.next() ) {
				Adiacenza a=new Adiacenza(idMapCountry.get(rs.getString("state1ab")),
						idMapCountry.get(rs.getString("state2ab")));
				list.add(a) ;
			}
			conn.close() ;
			return list ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	
	
}

