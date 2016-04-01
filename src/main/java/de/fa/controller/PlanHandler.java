package de.fa.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import de.fa.model.Route;
import de.fa.model.Station;

/**
 * Backing bean for the route viewer page.
 * @author Daniel
 *
 */
@ManagedBean
@SessionScoped
public class PlanHandler {

	private Map<String, Route> routeList;
	
	private String routeSelected;
	
	private List<Station> stations;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private UserTransaction transaction;
	
	/**
	 * Loads all available routes.
	 */
	@PostConstruct
	public void init(){
		try{
			transaction.begin();
			@SuppressWarnings("unchecked")
			List<Route> results = entityManager.createQuery("select k from Route k").getResultList();
			if(results != null && !results.isEmpty()){
				routeList = new TreeMap<String, Route>();
				for(Route r : results){
					r.getStations().size();		//call size to make JPA actually load the content of the list
					routeList.put(r.toString(), r);
				}
			}
			transaction.commit();
		}catch(Exception e){
			System.out.println("Exception! - " + e.getMessage());
		}
	}
	
	public PlanHandler(){
		stations = Collections.emptyList();
		routeList = Collections.emptyMap();
	}
	
	/**
	 * Displays the stations of the selected route.
	 */
	public void display(){
		if(routeSelected != null){
			stations = routeList.get(routeSelected).getStations();
		}else{
			stations = Collections.emptyList();			
		}
	}
	
	public Map<String, Route> getRouteList() {
		return routeList;
	}
	
	public void setRouteList(Map<String, Route> routes) {
		this.routeList = routes;
	}
	
	public String getRouteSelected() {
		return routeSelected;
	}
	
	public void setRouteSelected(String route) {
		this.routeSelected = route;
	}
	
	public List<Station> getStations() {
		return stations;
	}
	
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
}
