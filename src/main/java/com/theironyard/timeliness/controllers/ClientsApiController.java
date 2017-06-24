package com.theironyard.timeliness.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theironyard.timeliness.domain.Client;
import com.theironyard.timeliness.domain.ClientRepository;
import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;

@RestController
@RequestMapping("/api/clients")
public class ClientsApiController {

	private ClientRepository clients;
	private TimeWatcherRepository watchers;
	
	public ClientsApiController(ClientRepository clients, TimeWatcherRepository watchers) {
		this.clients = clients;
		this.watchers = watchers;
	}
	
	@GetMapping("")
	public List<Client> getAll(Authentication auth) {
		return getWatcherClients(auth);
	}
	
	@GetMapping("/{id}")
	public Client get(Authentication auth, @PathVariable Long id, HttpServletResponse response) {
		Client client = clients.findOne(id);
		if (client != null && client.getWatcher().getId() == getWatcher(auth).getId()) {
			return client;
		} else if (client == null) {
			response.setStatus(404);
			return null;
		}
		throw new AccessDeniedException("Cannot access client with id of " + id);
	}
	
	@PostMapping("")
	public List<Client> createClient(Authentication auth, @RequestBody Client client) {
		client.setWatcher(getWatcher(auth));
		clients.save(client);
		return getWatcherClients(auth);
	}
	
	@PutMapping("/{id}")
	public Client updateClient(Authentication auth, @RequestBody Client client, BindingResult result, @PathVariable Long id, HttpServletResponse response) {
		if (!client.getId().equals(id) || result.hasErrors()) {
			response.setStatus(400);
			return null;
		}

		client.setWatcher(getWatcher(auth));
		return clients.save(client);
	}
	
	@PostMapping("/deactivations")
	public Client deactivateClient(Authentication auth, @RequestBody Client client, HttpServletResponse response) {
		TimeWatcher watcher = getWatcher(auth);
		client = clients.findOne(client.getId());
		if (client != null && client.getWatcher().getId().equals(watcher.getId())) {
			client.deactivate();
			return clients.save(client);
		} else if (client == null) {
			response.setStatus(404);
			return null;
		}
		throw new AccessDeniedException("Cannot access client to deactivate");
	}
	
	@PostMapping("/activations")
	public Client activateClient(Authentication auth, @RequestBody Client client, HttpServletResponse response) {
		client = clients.findOne(client.getId());
		TimeWatcher watcher = getWatcher(auth);
		client = clients.findOne(client.getId());
		if (client != null && client.getWatcher().getId().equals(watcher.getId())) {
			client.activate();
			return clients.save(client);
		} else if (client == null) {
			response.setStatus(404);
			return null;
		}
		throw new AccessDeniedException("Cannot access client to deactivate");
	}

	private List<Client> getWatcherClients(Authentication auth) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		List<Client> watcherClients = clients.findAllByWatcher(watcher, new Sort(new Order("name")));
		return watcherClients;
	}

	private TimeWatcher getWatcher(Authentication auth) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		return watcher;
	}
	
}
