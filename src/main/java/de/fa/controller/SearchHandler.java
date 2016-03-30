package de.fa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.fa.model.ClockTime;
import de.fa.model.Route;
import de.fa.model.SearchResult;
import de.fa.model.Station;

@ManagedBean
@SessionScoped
@Named(value="SearchHandler")
public class SearchHandler {
	
	
	private Station start;
	private Station target;
	private ClockTime time;
	
	private ArrayList<SearchResult> searchResult;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Resource
	private UserTransaction transaction;

	@PostConstruct
	public void init(){
		try{
			transaction.begin();
			
			List<?> station = entityManager.createQuery("select k from Station k ").getResultList();
			if(station.size() == 0){
				//Linie 10
				
				List<Station> stations10 = new ArrayList<Station>();
				
				stations10.add(new Station("Gröpelingen"));
				stations10.add(new Station("Lindenhofstraße"));
				stations10.add(new Station("Moorstraße"));
				stations10.add(new Station("Altenescher Straße"));
				stations10.add(new Station("Waller Fiedhof"));
				stations10.add(new Station("Waller Straße"));
				stations10.add(new Station("Waldau-Theater"));
				stations10.add(new Station("Gustavstraße"));
				stations10.add(new Station("Utbremer Straße"));
				stations10.add(new Station("Wartburgerstraße"));
				stations10.add(new Station("Hansestraße"));
				stations10.add(new Station("Haferkamp"));
				
				List<Station> stations2 = new ArrayList<>(stations10);
				
				stations10.add(new Station("Doventorsteinweg"));
				stations10.add(new Station("Daniel-von-Büren-Straße"));
				stations10.add(new Station("Falkenstraße"));
				stations10.add(new Station("Hauptbahnhof"));
				
				for (Station s: stations10) {
					entityManager.persist(s);
				}
				
				//Line 2 extension
				stations2.add(new Station("Lloydstraße"));
				stations2.add(new Station("Doventor"));
				stations2.add(new Station("Radio Bremen/VHS"));
				stations2.add(new Station("Am Brill"));
				
				for(int i = 12; i < stations2.size(); ++i){
					entityManager.persist(stations2.get(i));
				}
				
				List<Station> stations2inv = new ArrayList<Station>(stations2);
				List<Station> stations10inv = new ArrayList<Station>(stations10);
				Collections.reverse(stations2inv);
				Collections.reverse(stations10inv);
				
				Route S2 = new Route("2", "Sebaldsbrück", stations2);
				Route G2 = new Route("2", "Gröpelingen", stations2inv);
				Route S10 = new Route("10", "Sebaldsbrück", stations10);
				Route G10 = new Route("10", "Gröpelingen", stations10inv);
				
				entityManager.persist(S2);
				entityManager.persist(G2);
				entityManager.persist(S10);
				entityManager.persist(G10);
			}
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public SearchHandler(){
		searchResult = new ArrayList<>();
	}
	
	public void search(){
		searchResult.add(new SearchResult("a", "b", "c", "d"));
	}
	
	public void doIt(){
		System.out.println("---------------test-------------");
	}

	public List<Station> searchStation(String name){
		
		Query q = entityManager.createQuery("select k from Station k where k.name = :name");
		
		q.setParameter("name", name);
		return q.getResultList();
	}

	public ArrayList<SearchResult> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(ArrayList<SearchResult> searchResult) {
		this.searchResult = searchResult;
	}
	
	public Station getStart() {
		return start;
	}

	public void setStart(Station start) {
		this.start = start;
	}

	public Station getTarget() {
		return target;
	}

	public void setTarget(Station target) {
		this.target = target;
	}

	public ClockTime getTime() {
		return time;
	}

	public void setTime(ClockTime time) {
		this.time = time;
	}

}
