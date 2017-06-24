package com.theironyard.timeliness.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
public class SessionApiController {
	
	private UserDetailsService userDetails;
	private AuthenticationManager authenticator;
	
	public SessionApiController(UserDetailsService userDetails, AuthenticationManager authenticator) {
		this.userDetails = userDetails;
		this.authenticator = authenticator;
	}

	@PutMapping("/mine")
	public Boolean login(@RequestBody Credentials credentials) {
		UserDetails details = userDetails.loadUserByUsername(credentials.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, credentials.password, details.getAuthorities());

        authenticator.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        return token.isAuthenticated();
	}
	
	@DeleteMapping("/mine")
	public Boolean logout(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, auth);
		return true;
	}
	
	static class Credentials {
		private String username;
		private String password;
		
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}
	
}
