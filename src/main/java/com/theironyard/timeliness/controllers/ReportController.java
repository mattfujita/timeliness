package com.theironyard.timeliness.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theironyard.timeliness.domain.Client;
import com.theironyard.timeliness.domain.ClientRepository;
import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;
import com.theironyard.timeliness.domain.WorkSpan;
import com.theironyard.timeliness.domain.WorkSpanRepository;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	private ClientRepository clients;
	private TimeWatcherRepository watchers;
	private WorkSpanRepository spans;

	public ReportController(ClientRepository clients, TimeWatcherRepository watchers, WorkSpanRepository spans) {
		this.clients = clients;
		this.watchers = watchers;
		this.spans = spans;
	}

	@GetMapping("")
	public String showReport(Authentication auth, Model model) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		List<Client> watcherClients = clients.findAllByWatcher(watcher, new Sort(new Order("name")));
		model.addAttribute("clients", watcherClients);
		model.addAttribute("report", new ArrayList<String>());
		return "report/index";
	}
	
	@PostMapping("")
	public String generateReport(Authentication auth, Client client, Model model) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		Client realClient = clients.findOneByIdAndWatcher(client.getId(), watcher);
		List<ClientViewModel> watcherClients = clients.findAllByWatcher(watcher, new Sort(new Order("name")))
				.stream()
				.map(c -> new ClientViewModel(c, client.getId()))
				.collect(Collectors.toList());
		

		Collection<MonthWorkViewModel> report = new ArrayList<MonthWorkViewModel>();
		if (realClient != null) {
			report = calculateReport(realClient);
		}

		model.addAttribute("clients", watcherClients);
		model.addAttribute("clientId", client.getId());
		model.addAttribute("report", report);
		return "report/index";
	}
	
	private Collection<MonthWorkViewModel> calculateReport(Client client) {
		Map<Date, MonthWorkViewModel> entriesMap = new HashMap<Date, MonthWorkViewModel>();
		
		List<WorkSpan> work = spans.findAllByClient(client);
		for (WorkSpan span : work) {
			Date monthified = monthify(span.getFromTime());
			if (!entriesMap.containsKey(monthified)) {
				entriesMap.put(monthified, new MonthWorkViewModel(monthified));
			}
			MonthWorkViewModel model = entriesMap.get(monthified);
			model.add(span);
		}
		
		List<MonthWorkViewModel> entries = new ArrayList<MonthWorkViewModel>(entriesMap.values());
		Collections.sort(entries, (left, right) -> left.getMonth().compareTo(right.getMonth()));
		return entries;
	}
	
	private static Date monthify(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0);
		return c.getTime();
	}
	
	static class MonthWorkViewModel {
		
		private Date month;
		private long numberOfHours;
		
		public MonthWorkViewModel(Date month) {
			this.month = month;
		}

		public void add(WorkSpan span) {
			if (span.getToTime() != null) {
				long s = (span.getToTime().getTime() - span.getFromTime().getTime()) / 1000;
				long m = s / 60 + 60;
				m = m - m % 60;
				numberOfHours += m / 60;
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
			return numberOfHours;
		}
		
	}
	
	static class ClientViewModel {
		
		private Client client;
		private boolean isSelected;
		
		public ClientViewModel(Client client, Long activeClientId) {
			this.client = client;
			this.isSelected = client.getId() == activeClientId;
		}
		
		public Long getId() {
			return client.getId();
		}
		
		public String getName() {
			return client.getName();
		}
		
		public boolean getIsSelected() {
			return isSelected;
		}
	}
	
}
