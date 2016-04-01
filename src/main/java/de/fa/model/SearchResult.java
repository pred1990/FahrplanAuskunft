package de.fa.model;

import java.io.Serializable;

/**
 * Instances of this class are displayed as results when using the route search page (search.xhtml).
 * A SearchResult has a station, a line and a time of arrival/departure.
 * @author Daniel
 *
 */
public class SearchResult implements Serializable{
	
	private static final long serialVersionUID = 762560152864528174L;
	private String station;
	private String route;
	private String time;
	
	public SearchResult(){}
	
	public SearchResult(Station station, Route route, ClockTime time){
		this(station.toString(), route.toString(), time.toString());
	}
	
	public SearchResult(String station, String line, String time){
		this.station = station;
		this.route = line;
		this.time = time;
	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getLine() {
		return route;
	}

	public void setLine(String line) {
		this.route = line;
	}
	
}
