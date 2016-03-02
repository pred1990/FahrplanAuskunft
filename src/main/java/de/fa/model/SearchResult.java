package de.fa.model;

import java.io.Serializable;

public class SearchResult implements Serializable{
	
	private static final long serialVersionUID = 762560152864528174L;
	private ClockTime depature;
	private ClockTime arrival;
	private String station;
	private String line;
	
	
	public SearchResult(){
		
	}
	
	public SearchResult(ClockTime depature, ClockTime arrival, String station, String line){
		this.depature = depature;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}

	public ClockTime getDepature() {
		return depature;
	}

	public void setDepature(ClockTime depature) {
		this.depature = depature;
	}

	public ClockTime getArrival() {
		return arrival;
	}

	public void setArrival(ClockTime arrival) {
		this.arrival = arrival;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	
	
}
