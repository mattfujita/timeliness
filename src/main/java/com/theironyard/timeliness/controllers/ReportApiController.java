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

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theironyard.timeliness.domain.Client;
import com.theironyard.timeliness.domain.ClientRepository;
import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;
import com.theironyard.timeliness.domain.WorkSpan;
import com.theironyard.timeliness.domain.WorkSpanRepository;

@RestController
@RequestMapping("/api/report")
public class ReportApiController {
	
	private TimeWatcherRepository watchers;
	private WorkSpanRepository spans;
	private ClientRepository clients; 

	public ReportApiController(ClientRepository clients, TimeWatcherRepository watchers, WorkSpanRepository spans) {
		this.watchers = watchers;
		this.spans = spans;
		this.clients = clients;
	}
	
	@PostMapping("")
	public Collection<MonthWorkViewModel> generateReport(Authentication auth, @RequestBody Client client, HttpServletResponse response) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		client = clients.findOneByIdAndWatcher(client.getId(), watcher);
		if (client != null) {
			return calculateReport(client);
		}
		response.setStatus(404);
		return null;
	}
	
	private Collection<MonthWorkViewModel> calculateReport(Client client) {
		Map<String, MonthWorkViewModel> entriesMap = new HashMap<String, MonthWorkViewModel>();
		
		List<WorkSpan> work = spans.findAllByClient(client);
		for (WorkSpan span : work) {
			Date monthified = monthify(span.getFromTime());
			String key = stringifiedMonth(span.getFromTime());
			if (!entriesMap.containsKey(key)) {
				entriesMap.put(key, new MonthWorkViewModel(monthified));
			}
			MonthWorkViewModel model = entriesMap.get(key);
			model.add(span);
		}
		
		List<MonthWorkViewModel> entries = new ArrayList<MonthWorkViewModel>(entriesMap.values());
		Collections.sort(entries, (left, right) -> left.getMonth().compareTo(right.getMonth()));
		return entries;
	}
	
	private static String stringifiedMonth(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		return formatter.format(date);
	}
	
	private static Date monthify(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0);
		return c.getTime();
	}
	
}
