package com.theironyard.timeliness.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSpanRepository extends JpaRepository<WorkSpan, Long> {

	public List<WorkSpan> findAllByWatcherAndFromTimeGreaterThanOrderByFromTime(TimeWatcher watcher, Date fromTime);
	
}
