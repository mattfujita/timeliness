package com.theironyard.timeliness.domain;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

	public List<Client> findAllByWatcher(TimeWatcher watcher, Sort sort);
	public Client findOneByIdAndWatcher(Long id, TimeWatcher watcher);
	
	
	@Query("from Client c where c.isActive = true and c.watcher = ?1 order by c.name")
	public List<Client> findAllActiveByWatcher(TimeWatcher watcher);
	
}
