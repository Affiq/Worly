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
class QuizTemplateServiceTest {

	@Autowired
	QuizTemplateService qts;
	
	@Autowired
	QuizTemplateRepository quizTempRepos;
	
	String testQuizName1 = "Test Quiz Template";
	String testQuizName2 ="Quiz Template 2";
	QuizTemplate targetQuizTemplate1;
	QuizTemplate targetQuizTemplate2;
	
	@Test
	void addQuizTemplateTest() {
		qts.addQuizTemplate(testQuizName1);
		targetQuizTemplate1 = quizTempRepos.findByQuizName(testQuizName1).get();
		assertEquals(targetQuizTemplate1.getQuizName(), testQuizName1);
	}
	
	@Test
	void getAllQuizTemplatesTest() {
		
		// checks if the target list is of correct size after adding test quiz template
		// also checks that the quiz names match
		List<QuizTemplate> targetList = qts.getAllQuizTemplates();
		assertEquals(targetList.size(), 1);
		assertEquals(targetList.get(0).getQuizName(), testQuizName1);
		
		// checks if the target list is of correct size after adding another quiz template
		// also checks if the quiz names match
		qts.addQuizTemplate(testQuizName2);
		targetList = qts.getAllQuizTemplates();
		assertEquals(targetList.size(), 2);
		assertEquals(targetList.get(0).getQuizName(), testQuizName1);
		assertEquals(targetList.get(1).getQuizName(), testQuizName2);
	}
	
	// also tests getAvailableQuizzes
	@Test
	void releaseQuizTemplateTest() {
		
		// checks that there are no available quizzes firstly
		List<QuizTemplate> targetList = qts.getAvailableQuizzes();
		assertEquals(targetList.size(), 0);

		// checks the list after releasing a quiz template
		// checks size and contents of retrieved list
		targetQuizTemplate1 = quizTempRepos.findByQuizName(testQuizName1).get();
		qts.releaseQuizTemplate(targetQuizTemplate1.getQuizTemplateID());
		targetList = qts.getAvailableQuizzes();
		assertEquals(targetList.size(), 1);
		assertEquals(targetList.get(0).getQuizName(), testQuizName1);

		// checks the list again after releasing a quiz template
		// checks size and contents of retrieved list
		targetQuizTemplate2 = quizTempRepos.findByQuizName(testQuizName2).get();
		qts.releaseQuizTemplate(targetQuizTemplate2.getQuizTemplateID());
		targetList = qts.getAvailableQuizzes();
		assertEquals(targetList.size(), 2);
		assertEquals(targetList.get(0).getQuizName(), testQuizName1);
		assertEquals(targetList.get(1).getQuizName(), testQuizName2);
	}

}
