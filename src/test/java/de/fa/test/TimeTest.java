package de.fa.test;

import de.fa.model.ClockTime;

public class TimeTest {

	public static void main(String[] args){
		ClockTime begin = 	new ClockTime(23, 55);
		ClockTime end = 	new ClockTime(00, 15);
		int diff = ClockTime.timeBetween(begin, end);
		System.out.println(begin + " to " + end + " : " + diff);
	}
}
