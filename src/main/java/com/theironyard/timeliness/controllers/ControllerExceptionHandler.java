package com.theironyard.timeliness.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.theironyard.timeliness.domain.TimeWatcher;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<?> redirectToLoginForAuthenticationWithNoPrincipal(HttpServletRequest request, Authentication auth, Exception e) throws Exception {
		if ((e != null && (e.getClass().equals(UsernameNotFoundException.class) || e.getClass().equals(BadCredentialsException.class))) || auth == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
		}

		TimeWatcher watcher = (TimeWatcher) auth.getPrincipal();
		if (watcher.getId() == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Location", "/session/new");
			return new ResponseEntity<Boolean>(headers, HttpStatus.valueOf(302));
		}
		throw e;
	}
	
}
