package de.fa.controller;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fa.model.ClockTime;
import de.fa.model.SearchResult;
import de.fa.model.Station;

@ManagedBean
@SessionScoped
public class SearchHandler {
	
	
	private Station start;
	private Station target;
	private ClockTime time;
	
	private ArrayList<SearchResult> searchResult;

	public SearchHandler(){
		searchResult = new ArrayList<>();
	}
	
	public void search(){
		searchResult.add(new SearchResult("a", "b", "c", "d"));
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
