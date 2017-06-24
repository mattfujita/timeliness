package com.theironyard.timeliness.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherService;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {
	
	private TimeWatcherService watchers;
	
	public UsersApiController(TimeWatcherService watchers) {
		this.watchers = watchers;
	}
	
	@PostMapping("")
	public TimeWatcher signUp(@RequestBody TimeWatcher watcher) {
		return watchers.signUpAndLogin(watcher.getUsername(), watcher.getPassword(), SecurityContextHolder.getContext());
	}

}
