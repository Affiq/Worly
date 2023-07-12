package com.example.worly.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.worly.model.Question;
import com.example.worly.model.QuizTemplate;

@Transactional
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
		
	void deleteByQuestionID(long questionID);
	
	Question findByQuestionText(String questionText);
	
	List<Question> findAllByQuizTemplate(QuizTemplate quizTemplate);
	
	Optional<Question> findByQuestionID(long questionID);
}