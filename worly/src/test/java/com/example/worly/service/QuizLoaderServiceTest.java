package com.example.worly.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.worly.model.QuizTemplate;
import com.example.worly.repository.QuizTemplateRepository;

// Test to see if quiz templates and respective details are generated
@SpringBootTest
class QuizLoaderServiceTest {

	@Autowired
	QuizTemplateService qts;
	
	@Autowired
	QuizLoaderService qls;
	
	@Autowired
	QuizTemplateRepository quizTempRepos;
	
	String testQuizName1 = "Test Quiz Template";
	QuizTemplate targetQuizTemplate1;
	
	
	@Test 
	void quizTemplateExistsIsFalseTest() {
		long falseID = 999999; // We are assuming that we won't have this many quizzes...
		
		boolean exists = qls.quizTemplateExists(falseID);
		
		assertFalse(exists);
	}
	
	
	@Test
	void questionExistsIsFalseTest() {
		long falseID = 999999; // We are assuming that we won't have this many questions.
		
		boolean exists = qls.questionExists(falseID);
		
		assertFalse(exists);
	}
	
	
	@Test
	void answerExistsIsFalseTest() {
		long falseID = 999999; // We are assuming that we won't have this many answers
		
		boolean exists = qls.answerExists(falseID);
		
		assertFalse(exists);
	}
	

}
