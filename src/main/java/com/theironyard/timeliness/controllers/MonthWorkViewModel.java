package com.theironyard.timeliness.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.theironyard.timeliness.domain.WorkSpan;

class MonthWorkViewModel {
	
	private Date month;
	private long numberOfMinutes;
	
	public MonthWorkViewModel(Date month) {
		this.month = month;
	}

	public void add(WorkSpan span) {
		if (span.getToTime() != null) {
			long s = (span.getToTime().getTime() - span.getFromTime().getTime()) / 1000;
			numberOfMinutes = s / 60;
		}
	}
	
	public String getMonthName() {
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM");
		return formatter.format(month);
	}
	
	public String getYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(month);
		return String.valueOf(c.get(Calendar.YEAR));
	}
	
	public Date getMonth() {
		return month;
	}
	
	public long getNumberOfHoursWorked() {
		return numberOfMinutes / 60 + 1;
	}
	
}