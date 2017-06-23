package com.theironyard.timeliness.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WorkSpanContext {
	
	private WorkSpanRepository spans;
	private ClientRepository clients;
	
	public WorkSpanContext(WorkSpanRepository spans, ClientRepository clients) {
		this.spans = spans;
		this.clients = clients;
	}
	
	public List<WorkSpan> findAllByWatcherAndFromTimeGreaterThanAndToTimeLessThanOrderByFromTime(TimeWatcher watcher, Date fromTime, Date toTime) {
		return spans.findAllByWatcherAndFromTimeGreaterThanAndFromTimeLessThanOrderByFromTime(watcher, fromTime, toTime);
	}
	
	public WorkSpan complete(TimeWatcher watcher, WorkSpan span) {
		return complete(watcher, span.getId());
	}
	
	public WorkSpan complete(TimeWatcher watcher, Long id) {
		WorkSpan span = spans.findOne(id);
		if (watcher.getId() == span.getWatcher().getId()) {
			span.complete();
			span = spans.save(span);
			return span;
		}
		return null;
	}
	
	public WorkSpan completeOpenAndCreateNew(TimeWatcher watcher, WorkSpan span) {
		Client client = clients.findOne(span.getClient().getId());
		if (client.getWatcher().getId() == watcher.getId()) {
			WorkSpan incomplete = spans.findIncompleteByWatcher(watcher);
			if (incomplete != null) {
				incomplete.complete();
				spans.save(incomplete);
			}
			
			span.setClient(client);
			span.setFromTime(Calendar.getInstance().getTime());
			span.setWatcher(watcher);
			span = spans.save(span);
			
			return span;
		}
		return null;
	}
	
}
