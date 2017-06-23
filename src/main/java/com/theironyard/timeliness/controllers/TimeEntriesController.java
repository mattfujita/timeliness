package com.theironyard.timeliness.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.theironyard.timeliness.domain.WorkSpanContext;

@Controller
@RequestMapping({"/entries", "/"})
public class TimeEntriesController {

	private WorkSpanContext spans;
	private TimeWatcherRepository watchers;
	private ClientRepository clients;

	public TimeEntriesController(WorkSpanContext spans, TimeWatcherRepository watchers, ClientRepository clients) {
		this.spans = spans;
		this.watchers = watchers;
		this.clients = clients;
	}
	
	@GetMapping("")
	public String getEntryForm(Authentication auth, Model model) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		Date fromTime = c.getTime();
		c.add(Calendar.DATE, 1);
		Date toTime = c.getTime();
		List<WorkSpanViewModel> models = spans.findAllByWatcherAndFromTimeGreaterThanAndToTimeLessThanOrderByFromTime(watcher, fromTime, toTime)
				.stream()
				.map(span -> new WorkSpanViewModel(span))
				.collect(Collectors.toList());
		
		List<Client> activeClients = clients.findAllActiveByWatcher(watcher);
		
		model.addAttribute("clients", activeClients);
		model.addAttribute("entries", models);
		model.addAttribute("showSomething", models.size() > 0 || activeClients.size() > 0);
		model.addAttribute("user", auth.getPrincipal());
		return "entries/index";
	}
	
	@PostMapping("/completions")
	public String completeWorkSpan(Authentication auth, WorkSpan span) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		spans.complete(watcher, span);
		return "redirect:/entries";
	}
	
	@PostMapping("")
	public String startWorkSpan(Authentication auth, WorkSpan span) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		spans.completeOpenAndCreateNew(watcher, span);
		return "redirect:/entries";
	}
	
	static class WorkSpanViewModel {
		
		private WorkSpan span;
		
		public WorkSpanViewModel(WorkSpan span) {
			this.span = span;
		}
		
		public Long getId() {
			return span.getId();
		}
		
		public String getName() {
			return span.getClient().getName();
		}
		
		public String getFromTime() {
			SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
			return formatter.format(span.getFromTime());
		}
		
		public String getToTime() {
			Date to = span.getToTime();
			if (to == null) return null;
			
			SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
			return formatter.format(span.getToTime());
		}
	}
	
}
