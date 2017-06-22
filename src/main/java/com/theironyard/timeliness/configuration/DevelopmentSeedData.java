package com.theironyard.timeliness.configuration;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.theironyard.timeliness.domain.Client;
import com.theironyard.timeliness.domain.ClientRepository;
import com.theironyard.timeliness.domain.TimeWatcher;
import com.theironyard.timeliness.domain.TimeWatcherRepository;
import com.theironyard.timeliness.domain.WorkSpan;
import com.theironyard.timeliness.domain.WorkSpanRepository;

@Configuration
@Profile("development")
public class DevelopmentSeedData {

	private TimeWatcherRepository watchers;
	private PasswordEncoder encoder;
	private ClientRepository clients;
	private WorkSpanRepository workSpans;
	
	public DevelopmentSeedData(TimeWatcherRepository watchers, PasswordEncoder encoder, ClientRepository clients, WorkSpanRepository workSpans) {
		this.watchers = watchers;
		this.encoder = encoder;
		this.clients = clients;
		this.workSpans = workSpans;
	}
	
	@PostConstruct
	public void InsertData() {
		TimeWatcher maria = watchers.save(new TimeWatcher("maria", encoder.encode("maria")));
		watchers.save(new TimeWatcher("hector", encoder.encode("hector")));
		
		Client zanzibar = clients.save(new Client("Zanzibar Unlimited", maria));
		Client brets = clients.save(new Client("Bret's Refrigeration", maria));
		Client allies = clients.save(new Client("Allie's Sweets", maria));
		
		Calendar c = Calendar.getInstance();
		
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 10, 0, 0);

		WorkSpan span = new WorkSpan(maria, zanzibar, c.getTime());
		c.add(Calendar.MINUTE, 52);
		span.setToTime(c.getTime());
		workSpans.save(span);
		
		c.add(Calendar.MINUTE, 12);
		span = new WorkSpan(maria, allies, c.getTime());
		c.add(Calendar.MINUTE, 49);
		span.setToTime(c.getTime());
		workSpans.save(span);
		
		c.add(Calendar.MINUTE, 57);
		span = new WorkSpan(maria, zanzibar, c.getTime());
		c.add(Calendar.MINUTE, 12);
		span.setToTime(c.getTime());
		workSpans.save(span);

		c.add(Calendar.MINUTE, 4);
		span = new WorkSpan(maria, brets, c.getTime());
		workSpans.save(span);
	}
	
}
