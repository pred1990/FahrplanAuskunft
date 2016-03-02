package de.fa.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SealedObject;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fa.model.ClockTime;
import de.fa.model.SearchResult;

@ManagedBean
@SessionScoped
public class SearchHandler {
	
	
	private String start;
	private String target;
	private String time;
	
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
