package com.theironyard.timeliness.domain;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TimeWatcherService {
	
	private PasswordEncoder encoder;
	private TimeWatcherRepository watchers;
	private TimeWatcherDetailsService watcherDetails;
	
	public TimeWatcherService(TimeWatcherRepository watchers, TimeWatcherDetailsService watcherDetails, PasswordEncoder encoder) {
		this.watcherDetails = watcherDetails;
		this.watchers = watchers;
		this.encoder = encoder;
	}

	public TimeWatcher signUpAndLogin(String username, String password, SecurityContext context) throws DataIntegrityViolationException {
		TimeWatcher watcher = new TimeWatcher(username, encoder.encode(password));
		watcher = watchers.save(watcher);
		
        UserDetails userDetails = watcherDetails.loadUserByUsername(watcher.getUsername());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, watcher.getPassword(), userDetails.getAuthorities());
        context.setAuthentication(token);

		return watcher;
	}
	
}
