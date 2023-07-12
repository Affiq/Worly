package com.example.worly.repository;

import com.example.worly.model.QuizTemplate;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizTemplateRepository extends JpaRepository<QuizTemplate, Long>{
		
	Optional<QuizTemplate> findByQuizTemplateID(long quizTemplateID);
	
	Optional<QuizTemplate> findByQuizName(String quizName);
	
	List<QuizTemplate> findAllByReleased(boolean released);
}

