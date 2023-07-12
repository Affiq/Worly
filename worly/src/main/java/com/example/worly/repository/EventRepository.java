package com.example.worly.repository;

import com.example.worly.model.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
		
	  Event findByEventTitle(String eventTitle);

	
}


