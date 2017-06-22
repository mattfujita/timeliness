package com.theironyard.timeliness.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TimeWatcherDetailsService implements UserDetailsService {

	private TimeWatcherRepository watchers;
	
	public TimeWatcherDetailsService(TimeWatcherRepository watchers) {
		this.watchers = watchers;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		TimeWatcher watcher = watchers.findByUsername(userName);
		if (watcher == null) {
			throw new UsernameNotFoundException("No user present with user name: " + userName);
		}
		return new TimelinessUserDetails(watcher);
	}

}
