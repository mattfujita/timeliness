package com.theironyard.timeliness.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class WorkSpanService {
	
	private WorkSpanRepository spans;
	private ClientRepository clients;
	
	public WorkSpanService(WorkSpanRepository spans, ClientRepository clients) {
		this.spans = spans;
		this.clients = clients;
	}
	
	public List<WorkSpan> findTodaysWorkSpansForTimeWatcher(TimeWatcher watcher) {
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
		Date fromTime = c.getTime();
		c.add(Calendar.DATE, 1);
		Date toTime = c.getTime();
		return findAllByWatcherAndFromTimeGreaterThanAndToTimeLessThanOrderByFromTime(watcher, fromTime, toTime);
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
