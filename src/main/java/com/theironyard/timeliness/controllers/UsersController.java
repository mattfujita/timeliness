package com.theironyard.timeliness.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherService;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	private TimeWatcherService watchers;
	
	public UsersController(TimeWatcherService watchers) {
		this.watchers = watchers;
	}

	@GetMapping("/new")
	public String getForm(Model model) {
		model.addAttribute("error", "");
		model.addAttribute("watcher", new TimeWatcher());
		return "users/form";
	}

	@PostMapping("")
	public String handleForm(@ModelAttribute("watcher") @Valid TimeWatcher watcher, BindingResult result, Model model) {
		if (!result.hasErrors()) {
			try {
				watchers.signUpAndLogin(watcher.getUsername(), watcher.getPassword(), SecurityContextHolder.getContext());
			} catch (DataIntegrityViolationException cve) {
				model.addAttribute("error", "That username is already taken.");
				return "users/form";
			}
	        return "redirect:/";
		}
		
		List<String> errors = result.getAllErrors().stream().map(e -> e.getArguments()[0].toString() + ": " + e.getDefaultMessage()).collect(Collectors.toList());
		model.addAttribute("error", StringUtils.collectionToCommaDelimitedString(errors));
		return "users/form";
	}
}
