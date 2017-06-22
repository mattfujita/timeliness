package com.theironyard.timeliness.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;
import com.theironyard.timeliness.domain.WorkSpan;
import com.theironyard.timeliness.domain.WorkSpanRepository;

@Controller
@RequestMapping({"/entries", "/"})
public class TimeEntriesController {

	private WorkSpanRepository spans;
	private TimeWatcherRepository watchers;

	public TimeEntriesController(WorkSpanRepository spans, TimeWatcherRepository watchers) {
		this.spans = spans;
		this.watchers = watchers;
	}
	
	@GetMapping("")
	public String getEntryForm(Authentication auth, Model model) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		
		List<WorkSpanViewModel> models = spans.findAllByWatcherOrderByFromTime(watcher)
				.stream()
				.map(span -> new WorkSpanViewModel(span))
				.collect(Collectors.toList());
		model.addAttribute("entries", models);
		model.addAttribute("user", auth.getPrincipal());
		return "entries/index";
	}
	
	static class WorkSpanViewModel {
		
		private WorkSpan span;
		
		public WorkSpanViewModel(WorkSpan span) {
			this.span = span;
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
			if (to == null) return "";
			
			SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
			return formatter.format(span.getToTime());
		}
	}
	
}
