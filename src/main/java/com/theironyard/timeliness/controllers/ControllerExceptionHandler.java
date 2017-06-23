package com.theironyard.timeliness.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.theironyard.timeliness.domain.TimeWatcher;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value=Exception.class)
	public String redirectToLoginForAuthenticationWithNoPrincipal(Authentication auth, Exception e) {
		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		if (watcher.getId() == null) {
			return "redirect:/session/new";
		}
		return "/error";
	}
	
}
