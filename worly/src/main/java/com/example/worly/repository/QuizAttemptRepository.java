package com.example.worly.repository;

import com.example.worly.model.Quiz_Attempt;

import java.sql.Time;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAttemptRepository extends JpaRepository<Quiz_Attempt, Long>{
		
	Quiz_Attempt findByDateAndTime(Date date, Time time);
	
}


