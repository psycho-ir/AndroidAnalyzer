package com.sarabadani.android.analyzer.model;

import java.util.Date;

public class Call {

	private String number;
	private String name;
	private int duration;
	private Date date;

	public Call(String number, String name, int duration, Date date) {
		this.number = number;
		this.name = name;
		this.duration = duration;
		this.date = date;

	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void increaseDuration(int duration2) {
		if(duration2> 0 ) this.duration += duration2;
		
	}
}
