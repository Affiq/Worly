package com.example.worly.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.worly.model.Answer;
import com.example.worly.model.Question;
import com.example.worly.model.QuizTemplate;
import com.example.worly.model.Recommendation;
import com.example.worly.repository.AnswerRepository;
import com.example.worly.repository.QuestionRepository;
import com.example.worly.repository.QuizTemplateRepository;
import com.example.worly.repository.RecommendationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuizLoaderService {

	@Autowired
	private  QuizTemplateRepository quizTempRepository;
	
	@Autowired
	private  QuestionRepository questionRepository;
	
	@Autowired
	private  AnswerRepository answerRepository;
	
	@Autowired
	private RecommendationRepository recommRepository;
	
	public List<Recommendation> getAnswerRecommendations(long answerID) {
		List<Recommendation> newList = new ArrayList<>();
		
		if (answerExists(answerID))
			newList = recommRepository.findAllByAnswer(
					answerRepository.findByAnswerID(answerID).get());
		
		return newList;
	}
	
	public List<Answer> getQuestionAnswers(long questionID) {
		List<Answer> newList = new ArrayList<>();
		
		if (questionExists(questionID))
			newList = answerRepository.findAllByQuestion(
					questionRepository.findByQuestionID(questionID).get());

		return newList;
	}
	
	public List<Question> getQuizQuestions(long quizTempID) {
		List<Question> newList = new ArrayList<>();
	
		if (quizTemplateExists(quizTempID))
			newList = questionRepository.findAllByQuizTemplate(
					quizTempRepository.findByQuizTemplateID(quizTempID).get()); // gets quiz template object
		
		return newList;
	}
	
	public List<Answer> getAllAnswers() {
		List<Answer> newList = new ArrayList<>();
		
		newList = answerRepository.findAll();
		return newList;
	}
	
	public List<Recommendation> getAllRecommendations() {
		List<Recommendation> newList = new ArrayList<>();
		
		newList = recommRepository.findAll();
		return newList;
	}
	
	public boolean quizTemplateExists(long quizTempID) {
		try {
			 QuizTemplate temp = quizTempRepository.findByQuizTemplateID(quizTempID).get();
			 return true; }
		catch (Exception e) {
			return false; }
	}
	
	public boolean questionExists(long questionID) {
		try {
			 Question temp = questionRepository.findByQuestionID(questionID).get();
			 return true; }
		catch (Exception e) {
			return false; }
	}
	
	public boolean answerExists(long answerID) {
		try {
			 Answer temp = answerRepository.findByAnswerID(answerID).get();
			 return true; }
		catch (Exception e) {
			return false; }
	}
	
	public Question getQuestion(long questionid) {
		return questionRepository.findByQuestionID(questionid).get();
	}
	
	public Answer getAnswer(long answerID) {
		return answerRepository.findByAnswerID(answerID).get();
	}
}
