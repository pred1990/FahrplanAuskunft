package de.fa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The ClockTime class represents a time of day, saved internally in minutes since 00:00. 
 * In contrast to java.util.Date, it does not refer to a specific date.
 * The range for valid values is from 00:00 to 23:59 (hours:minutes).
 * @author Daniel
 *
 */
@Entity
public class ClockTime implements Serializable {
	
	private static final long serialVersionUID = 2463076306164767605L;

	private static final Integer MAX_VALUE = 24 * 60;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer minutes;
	
	public ClockTime(){}
	
	public ClockTime(Integer hours, Integer minutes){
		this.minutes = hours * 60 + minutes;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Integer timeUntil(ClockTime end){
		return ClockTime.timeBetween(this, end);
	}
	
	public Integer timeSince(ClockTime begin){
		return ClockTime.timeBetween(begin, this);
	}
	
	public static Integer timeBetween(ClockTime begin, ClockTime end){
		int diff = end.minutes - begin.minutes;
		return diff >= 0 ? diff : diff + MAX_VALUE;
	}
	
	@Override
	public String toString(){
		return (minutes / 60) + ":" + (minutes % 60);
	}
	
}