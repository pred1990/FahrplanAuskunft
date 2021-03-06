package de.fa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The Station class represents a single station which may be visited by a public transport vehicle.
 * @author Daniel
 *
 */
@Entity
public class Station implements Serializable {
	
	private static final long serialVersionUID = 7614888473248106799L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	
	public Station(){}
	
	public Station(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the station.
	 */
	@Override
	public String toString(){
		return this.name;
	}
	
	/**
	 * Returns true if both the names of both stations are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Station)){
			return false;
		}
		Station s = (Station) obj;
		return this.getName().equals(s.getName());
	}
	
}
