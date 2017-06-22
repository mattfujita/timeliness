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
				.antMatchers(HttpMethod.POST, "/users/create", "/session/mine").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/session/new")
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/session/mine");
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
	
}
