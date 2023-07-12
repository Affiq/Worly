package com.example.worly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.worly.model.Question_Attempt;

@Repository
public interface QuestionAttemptRepository extends JpaRepository<Question_Attempt, Long>{
		
}
