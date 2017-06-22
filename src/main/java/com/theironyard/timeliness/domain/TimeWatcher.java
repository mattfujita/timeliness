package com.theironyard.timeliness.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class TimeWatcher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@NotEmpty
	@Column(unique=true)
	private String username;
	
	private String encryptedPassword;
	
	@Transient
	private String password;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="watcher")
	private List<Client> clients;
	
	public TimeWatcher() {
		this.username = "";
	}
	
	public TimeWatcher(TimeWatcher watcher) {
		this.id = watcher.id;
		this.username = watcher.username;
		this.encryptedPassword = watcher.encryptedPassword;
		this.password = watcher.password;
	}
	
	public TimeWatcher(String username, String encryptedPassword) {
		this.username = username;
		this.encryptedPassword = encryptedPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addClient(String name) {
		Client client = new Client(name, this);
		clients.add(client);
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
}
