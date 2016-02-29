package de.fa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private Route route;
	@ElementCollection(targetClass=ClockTime.class)
	private List<ClockTime> departures;
	
	public Trip(){}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
	
}
