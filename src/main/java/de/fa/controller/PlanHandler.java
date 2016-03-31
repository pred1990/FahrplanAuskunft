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
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.fa.model.Route;
import de.fa.model.Station;

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
	
	
	@PostConstruct
	public void init(){
		try{
			loadRoutesList();
//			showRoute();
		}catch(Exception e){
			System.out.println("Exception! - " + e.getMessage());
		}
	}
	
	private void loadRoutesList() throws Exception {
		transaction.begin();
		Query q = entityManager.createQuery("select k from Route k");
		List<Route> results = q.getResultList();
		if(results != null && !results.isEmpty()){
			routeList = new TreeMap<String, Route>();
			for(Route r : results){
				r.getStations().size();		//call size to make JPA actually load the content of the list
				routeList.put(r.toString(), r);
			}
		}
		transaction.commit();
	}
	
//	private void showRoute() throws Exception {
//		transaction.begin();
//		Query q = entityManager.createQuery("select k from Route k where k.name = :name AND k.direction = :direction");
//		q.setParameter("name", "2");
//		q.setParameter("direction", "Sebaldsbrück");
//		routeSelected = (Route) q.getSingleResult();
//		if(routeSelected != null){
//			List<Station> stationList = routeSelected.getStations();
//			if(stationList != null){
//				stations.size();	//call size() to make JPA actually load the content of the list
//				stations = stationList;
//			}
//		}else{
//			System.out.println("route nicht gefunden...");
//		}
//		transaction.commit();
//	}
	
	public PlanHandler(){
		stations = Collections.emptyList();
		routeList = Collections.emptyMap();
	}
	
	public void beep(){
		System.out.println("boop");
		System.out.println("selected route: " + (routeSelected == null ? "null" : routeSelected.toString()));
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
