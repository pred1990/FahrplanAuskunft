package de.fa.model;

import java.io.Serializable;

public class SearchResult implements Serializable{
	
	private static final long serialVersionUID = 762560152864528174L;
	private String departure;
	private String arrival;
	private String station;
	private String line;
	
	
	public SearchResult(){
		
	}
	/*
	public SearchResult(ClockTime departure, ClockTime arrival, String station, String line){
		this.departure = departure;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}
	*/
	public SearchResult(String departure, String arrival, String station, String line){
		this.departure = departure;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}

	
	
	public String getDeparture() {
		return departure;
	}
	public void setDepature(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
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
