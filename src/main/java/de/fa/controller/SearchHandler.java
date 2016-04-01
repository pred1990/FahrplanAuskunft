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
import de.fa.model.Route;
import de.fa.model.SearchResult;
import de.fa.model.Station;
import de.fa.model.Trip;

/**
 * Backing bean for the route search page.
 * @author Daniel
 *
 */
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

	}

	public SearchHandler(){
		searchResult = new ArrayList<>();
	}
	
	/**
	 * Searches for a Trip between the start and target stations that arrives before the set time. 
	 * Results will be added to the searchResult list. Any previous values in the list will be removed.
	 */
	@SuppressWarnings("unchecked")
	public void search(){
		try{
			transaction.begin();
			searchResult.clear();
			if(start != null && target != null && time != null){
				
				//find matching routes
				Query q = entityManager.createQuery("select k from Route k where :start MEMBER OF k.stations and :target MEMBER OF k.stations");
				List<Route> routes = q.setParameter("start", start).setParameter("target", target).getResultList();
				if(routes.isEmpty()){
					System.out.println("no route found");
				}
				
				//select those routes that go in the desired direction
				List<Route> validRoutes = new ArrayList<Route>();
				for(Route route : routes){
					for(Station station : route.getStations()){
						if(station.equals(start)){
							//stations in right order
							validRoutes.add(route);
							break;
						}
						if(station.equals(target)){
							//stations in wrong order
							break;
						}
					}
				}
				
				//find best trip in routes
				Trip bestTrip = null;
				int bestTime = Integer.MAX_VALUE;
				if(!validRoutes.isEmpty()){
					for(Route route : validRoutes){
						List<Trip> trips = entityManager.createQuery("select k from Trip k where k.route = :route").setParameter("route", route).getResultList();
						for(Trip trip : trips){
							int tripTime = ClockTime.timeBetween(trip.getTime(target), this.time.getMinutes());
							if(tripTime < bestTime){
								bestTrip = trip;
								bestTime = tripTime;
							}
						}
					}
				}else{
					System.out.println("no valid route found");
				}
				
				//a matching trip has been found
				if(bestTrip != null){
					searchResult.add(new SearchResult(start, bestTrip.getRoute(), new ClockTime(bestTrip.getTime(start))));
					searchResult.add(new SearchResult(target, bestTrip.getRoute(), new ClockTime(bestTrip.getTime(target))));
				}else{
					System.out.println("no trip found");
				}
			}
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Looks up and returns a list of Stations (generally none or one) with the given name.
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Station> searchStation(String name){
		return entityManager.createQuery("select k from Station k where k.name = :name").setParameter("name", name).getResultList();
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
