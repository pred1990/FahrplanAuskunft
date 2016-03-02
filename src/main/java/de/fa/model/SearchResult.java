package de.fa.model;

import java.io.Serializable;

public class SearchResult implements Serializable{
	
	private static final long serialVersionUID = 762560152864528174L;
	private String depature;
	private String arrival;
	private String station;
	private String line;
	
	
	public SearchResult(){
		
	}
	/*
	public SearchResult(ClockTime depature, ClockTime arrival, String station, String line){
		this.depature = depature;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}
	*/
	public SearchResult(String depature, String arrival, String station, String line){
		this.depature = depature;
		this.arrival = arrival;
		this.station = station;
		this.line = line;
	}

	
	
	public String getDepature() {
		return depature;
	}
	public void setDepature(String depature) {
		this.depature = depature;
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
