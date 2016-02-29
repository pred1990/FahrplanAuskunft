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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
