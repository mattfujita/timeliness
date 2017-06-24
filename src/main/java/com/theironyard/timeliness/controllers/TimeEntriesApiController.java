package com.theironyard.timeliness.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;
import com.theironyard.timeliness.domain.WorkSpan;
import com.theironyard.timeliness.domain.WorkSpanService;

@RestController
@RequestMapping("/api/entries")
public class TimeEntriesApiController {

	private WorkSpanService spans;
	private TimeWatcherRepository watchers;

	public TimeEntriesApiController(WorkSpanService spans, TimeWatcherRepository watchers) {
		this.spans = spans;
		this.watchers = watchers;
	}
	
	@GetMapping("")
	public List<WorkSpan> getEntryForm(Authentication auth, Model model) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		watcher = watchers.findOne(watcher.getId());
		return spans.findTodaysWorkSpansForTimeWatcher(watcher);
	}
	
	@PostMapping("/completions")
	public WorkSpan completeWorkSpan(Authentication auth, @RequestBody WorkSpan span) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		return spans.complete(watcher, span);
	}
	
	@PostMapping("")
	public List<WorkSpan> startWorkSpan(Authentication auth, @RequestBody WorkSpan span) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		spans.completeOpenAndCreateNew(watcher, span);
		return spans.findTodaysWorkSpansForTimeWatcher(watcher);
	}

}
