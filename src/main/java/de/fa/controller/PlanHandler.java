package de.fa.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fa.model.Route;
import de.fa.model.Station;

@ManagedBean
@SessionScoped
public class PlanHandler {

	private Route route;
	
	private List<Station> stations;

	@PersistenceContext
	private EntityManager entityManager;
	
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	public List<Station> getStations() {
		return stations;
	}
	
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	@PostConstruct
	public void init(){
		Query q = entityManager.createQuery("select k from Route k where k.name = :name AND k.direction = :direction");
		q.setParameter("name", "2");
		q.setParameter("direction", "Sebaldsbrück");
		route = (Route) q.getSingleResult();
		if(route != null){
			List<Station> stationlist = route.getStations();
			if(stationlist != null){
				System.out.println("stations not null! hooray...?");
				if(!stationlist.isEmpty()){
					System.out.println("stations not empty!");
				}
	//			for(Station station : route.getStations()){
	//				System.out.println(station.getName());
	//			}
			}
//			stations = route.getStations();			
		}else{
			System.out.println("route nicht gefunden...");
		}
	}
	
	public PlanHandler(){
		stations = Collections.emptyList();
	}
}
