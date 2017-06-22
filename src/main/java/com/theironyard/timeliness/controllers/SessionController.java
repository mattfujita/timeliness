package com.theironyard.timeliness.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {

	@GetMapping({"/session/new", "/login"})
	public String showLoginForm(@RequestParam Optional<String> error, Model model) {
		String errorMessage = "";
		if (error.isPresent()) {
			errorMessage = "Cannot log in with those credentials.";
		}
		model.addAttribute("name", false);
		model.addAttribute("error", errorMessage);
		return "session/login";
	}
	
}
