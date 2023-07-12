package com.example.worly.repository;

import com.example.worly.model.Answer;
import com.example.worly.model.Question;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>{
	
	
	Answer findByAnswerText(String answerText);
	
	Optional<Answer> findByAnswerID(long answerID);
	
	List<Answer> findAllByQuestion(Question question);
	
	void deleteByAnswerID(long answerID);
}

