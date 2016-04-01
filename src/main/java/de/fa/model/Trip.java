package de.fa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

/**
 * The Trip class represents a scheduled trip of a vehicle along a Route.
 * It contains the Route, as well as a list of departure times.
 * @author Daniel
 *
 */
@Entity
public class Trip implements Serializable {

	private static final long serialVersionUID = -3805361067439656091L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToOne(targetEntity=Route.class, optional=false)
	private Route route;
	@OrderColumn
	@ElementCollection(targetClass=Integer.class)
	private List<Integer> times;

	public Trip(){}
	
	/**
	 * Find the time in minutes (see ClockTime) at which the given Station is reached.
	 * Returns null if the Station is not a part of this trip.
	 * @param s
	 * @return
	 */
	public Integer getTime(Station s){
		List<Station> stations = route.getStations();
		for(int i = 0; i < stations.size(); ++i){
			if(stations.get(i).equals(s)){
				return times.get(i);
			}
		}
		return null;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
	public List<Integer> getTimes() {
		return times;
	}
	
	public void setTimes(List<Integer> times) {
		this.times = times;
	}
	
}
