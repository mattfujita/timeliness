package com.theironyard.timeliness.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeWatcherRepository extends JpaRepository<TimeWatcher, Long> {

	public TimeWatcher findByUsername(String userName);
	
}
