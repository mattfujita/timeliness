package com.theironyard.timeliness.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@NotEmpty
	private String name;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	private TimeWatcher watcher;
	
	private Boolean isActive;
	
	public Client() {
		this.name = "";
		this.isActive = true;
	}
	
	public Client(String name, TimeWatcher watcher) {
		this();
		this.name = name;
		this.watcher = watcher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TimeWatcher getWatcher() {
		return watcher;
	}

	public void setWatcher(TimeWatcher watcher) {
		this.watcher = watcher;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void deactivate() {
		setIsActive(false);
	}

	public void activate() {
		setIsActive(true);
	}

}
