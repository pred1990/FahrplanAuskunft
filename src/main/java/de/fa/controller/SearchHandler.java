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
	private Date date;
	private ClockTime time;
	
	private ArrayList<SearchResult> searchResult;

	public SearchHandler(){
		searchResult = new ArrayList<>();
	}
	
	public void search(){
		searchResult.add(new SearchResult(new ClockTime(12, 0), new ClockTime(12, 0), "test", "test"));
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ClockTime getTime() {
		return time;
	}

	public void setTime(ClockTime time) {
		this.time = time;
	}
	
	
}
