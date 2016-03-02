package de.fa.controller;

import java.util.Date;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.fa.model.ClockTime;

@ManagedBean
@SessionScoped
public class SearchHandler {
	
	
	private String start;
	private String target;
	private Date date;
	private ClockTime time;

	public SearchHandler(){
		
	}
	
	public void search(){
		
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
