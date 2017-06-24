package com.theironyard.timeliness.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherDetailsService;
import com.theironyard.timeliness.domain.TimeWatcherRepository;

@Controller
@RequestMapping("/users")
public class UsersController {
	
	private TimeWatcherDetailsService watcherDetails;
	private TimeWatcherRepository watchers;
	private PasswordEncoder encoder;
	
	public UsersController(TimeWatcherRepository watchers, TimeWatcherDetailsService watcherDetails, PasswordEncoder encoder) {
		this.watcherDetails = watcherDetails;
		this.watchers = watchers;
		this.encoder = encoder;
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
			watcher.setEncryptedPassword(encoder.encode(watcher.getPassword()));
			
			try {
				watchers.save(watcher);
			} catch (DataIntegrityViolationException cve) {
				model.addAttribute("error", "That username is already taken.");
				return "users/form";
			}
	        UserDetails userDetails = watcherDetails.loadUserByUsername(watcher.getUsername());
	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, watcher.getPassword(), userDetails.getAuthorities());

	        SecurityContextHolder.getContext().setAuthentication(token);
            return "redirect:/";
		}
		
		List<String> errors = result.getAllErrors().stream().map(e -> e.getArguments()[0].toString() + ": " + e.getDefaultMessage()).collect(Collectors.toList());
		model.addAttribute("error", StringUtils.collectionToCommaDelimitedString(errors));
		return "users/form";
	}
}
