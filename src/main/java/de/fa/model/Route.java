package de.fa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Route class represents a sequence of Stations which are visited by vehicles in that order.
 * Each route also contains an identifier which is split into two parts: 
 * the name of the route (generally an integer) and 
 * the direction (generally the name of the Route's final destination).
 * @author Daniel
 *
 */
@Entity
public class Route implements Serializable {

	private static final long serialVersionUID = 4956399724795853303L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String direction;
	@ElementCollection(targetClass=Station.class)
	private List<Station> stations;
	
	public Route(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	@Override
	public String toString(){
		return name + " " + direction;
	}
	
}