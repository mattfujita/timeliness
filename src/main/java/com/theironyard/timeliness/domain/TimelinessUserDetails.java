package com.theironyard.timeliness.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class TimelinessUserDetails extends TimeWatcher implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	public TimelinessUserDetails(TimeWatcher watcher) {
		super(watcher);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
	}

	@Override
	public String getPassword() {
		return super.getEncryptedPassword();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
