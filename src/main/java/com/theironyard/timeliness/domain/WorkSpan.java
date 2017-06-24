package com.theironyard.timeliness.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class WorkSpan {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	private TimeWatcher watcher;
	
	@ManyToOne
	private Client client;
	
	private Date fromTime;
	private Date toTime;
	
	public WorkSpan() {}
	
	public WorkSpan(TimeWatcher watcher, Client client, Date fromTime) {
		super();
		this.watcher = watcher;
		this.client = client;
		this.fromTime = fromTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TimeWatcher getWatcher() {
		return watcher;
	}

	public void setWatcher(TimeWatcher watcher) {
		this.watcher = watcher;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public void complete() {
		setToTime(Calendar.getInstance().getTime());
	}
	
}
