package com.theironyard.timeliness.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService users;
	
	public SecurityConfig(UserDetailsService users) {
		this.users = users;
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/users/new", "/session/new", "/img/**", "/app/**", "/css/**", "/js/**").permitAll()
				.antMatchers(HttpMethod.POST, "/users", "/session/mine", "/api/session/mine").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/session/new")
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/session/mine")
			.and()
				.addFilterAfter(new CsrfIntoCookieFilter(), CsrfFilter.class)
				.csrf()
				.csrfTokenRepository(tokenRepository());
	}
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder builder) throws Exception {
		builder
			.userDetailsService(users)
			.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public CsrfTokenRepository tokenRepository() {
		HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
	    tokenRepository.setHeaderName("X-XSRF-TOKEN");
	    tokenRepository.setParameterName("_csrf");
	    
	    return tokenRepository;
	}
	
}
