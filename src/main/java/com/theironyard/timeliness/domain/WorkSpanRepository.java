package com.theironyard.timeliness.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSpanRepository extends JpaRepository<WorkSpan, Long> {

	public List<WorkSpan> findAllByWatcherAndFromTimeGreaterThanAndFromTimeLessThanOrderByFromTime(TimeWatcher watcher, Date fromTime, Date toTime);
	
	@Query("from WorkSpan s where s.watcher = ?1 and s.toTime is null")
	public WorkSpan findIncompleteByWatcher(TimeWatcher watcher); 
	
}
