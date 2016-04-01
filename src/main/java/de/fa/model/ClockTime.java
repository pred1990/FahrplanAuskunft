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

	public static final Integer MAX_VALUE = 24 * 60;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Integer minutes;
	
	public ClockTime(){}
	
	public ClockTime(Integer hours, Integer minutes) throws IllegalArgumentException {
		if(hours < 0 || hours > 23|| minutes < 0 || minutes > 59){
			throw new IllegalArgumentException();
		}
		this.minutes = hours * 60 + minutes;
	}
	
	public ClockTime(Integer minutes){
		if(minutes >= MAX_VALUE){
			throw new IllegalArgumentException();
		}
		this.minutes = minutes;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	/**
	 * Calculates the time that would pass from the first passed ClockTime 
	 * until the second ClockTime would be reached.
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Integer timeBetween(ClockTime begin, ClockTime end){
		return ClockTime.timeBetween(begin.getMinutes(), end.getMinutes());
	}
	
	/**
	 * Calculates the time that would pass from the first passed time 
	 * until the second time would be reached. 
	 * Times are passed in minutes, e.g. "03:44" would be 224.
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Integer timeBetween(Integer begin, Integer end){
		int diff = end - begin;
		return diff >= 0 ? diff : diff + MAX_VALUE;
	}
	
	/**
	 * Returns a string representation of this ClockTime instance 
	 * formatted like the time on a digical clock (e.g. "23:59").
	 */
	@Override
	public String toString(){
		int hrs = getMinutes() / 60;
		int mins = getMinutes() % 60;
		String hrsStr = (hrs < 10) ? "0" + String.valueOf(hrs) : String.valueOf(hrs);
		String minStr = (mins < 10) ? "0" + String.valueOf(mins) : String.valueOf(mins);
		return hrsStr + ":" + minStr;
	}
	
}