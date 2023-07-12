package com.example.worly.repository;

import com.example.worly.model.Attend_Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendEventRepository extends JpaRepository<Attend_Event, Long>{
		
}


