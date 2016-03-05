package de.fa.controller;

import java.util.ArrayList;
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
			
			List<?> station = entityManager.createQuery("select k from Station k where k.name = 'Station'").getResultList();
				if(station.size() == 0){
					entityManager.persist(new Station("Station"));
				}
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public SearchHandler(){
		searchResult = new ArrayList<>();
	}
	
	public void search(){
		searchResult.add(new SearchResult("a", "b", "c", "d"));
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
