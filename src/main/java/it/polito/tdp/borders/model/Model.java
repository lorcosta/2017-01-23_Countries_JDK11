package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private Graph<Country,DefaultEdge> graph;
	private BordersDAO dao=new BordersDAO();
	private Map<String,Country> idMapCountry;
	
	public List<Country> creaGrafo(Integer anno) {
		this.idMapCountry=new HashMap<>();
		this.graph=new SimpleGraph<>(DefaultEdge.class);
		Graphs.addAllVertices(this.graph, dao.loadAllCountries(idMapCountry));
		for(Country c:this.graph.vertexSet()) {
			c.setConfinanti(0);
		}
		List<Adiacenza> adiacenze=dao.listAdiacenze(anno,idMapCountry);
		for(Adiacenza a:adiacenze) {
			if(this.graph.containsVertex(a.getC1()) && this.graph.containsVertex(a.getC2())&&
					!this.graph.containsEdge(a.getC1(),a.getC2()) && !a.getC1().equals(a.getC2())){
						Graphs.addEdgeWithVertices(this.graph, a.getC1(), a.getC2());
			}
		}
		List<Country> statiOrdinati=new ArrayList<>(this.graph.vertexSet());
		for(Country c:this.graph.vertexSet()) {
			c.setConfinanti(Graphs.neighborListOf(this.graph, c).size());
			statiOrdinati.add(c);
		}
		Collections.sort(statiOrdinati);
		return statiOrdinati;
	}
}
