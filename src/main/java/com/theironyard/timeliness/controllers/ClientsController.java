package com.theironyard.timeliness.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theironyard.timeliness.domain.Client;
import com.theironyard.timeliness.domain.ClientRepository;
import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;

@Controller
@RequestMapping("/clients")
public class ClientsController {
	
	private ClientRepository clients;
	private TimeWatcherRepository watchers;
	
	public ClientsController(ClientRepository clients, TimeWatcherRepository watchers) {
		this.clients = clients;
		this.watchers = watchers;
	}

	@GetMapping("")
	public String listClients(Model model, Authentication auth) {
		model.addAttribute("clients", getWatcherClients(auth));
		return "clients/index";
	}
	
	@PostMapping("")
	public String createNewClient(@ModelAttribute("client") @Valid Client client, BindingResult result, Model model, Authentication auth) {
		if (result.hasErrors()) {
			model.addAttribute("errors", "You must provide a name.");
			return "clients/form";
		}
		client.setWatcher(getWatcher(auth));
		clients.save(client);
		return "redirect:/clients";
	}
	
	@GetMapping("/new")
	public String showForm(Model model, Authentication auth) {
		model.addAttribute("errors", "");
		model.addAttribute("clients", getWatcherClients(auth));
		model.addAttribute("client", new Client());
		return "clients/form";
	}
	
	@GetMapping("/{id}")
	public String showForm(Model model, @PathVariable Long id, Authentication auth) {
		model.addAttribute("errors", "");
		model.addAttribute("clients", getWatcherClients(auth));
		model.addAttribute("client", clients.findOne(id));
		return "clients/form";
	}
	
	@PutMapping("/{id}")
	public String updateClient(@ModelAttribute("client") @Valid Client client, BindingResult result, Model model, Authentication auth) {
		if (result.hasErrors()) {
			model.addAttribute("errors", "You must provide a name.");
			return "clients/form";
		}
		client.setWatcher(getWatcher(auth));
		clients.save(client);
		return "redirect:/clients";
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
