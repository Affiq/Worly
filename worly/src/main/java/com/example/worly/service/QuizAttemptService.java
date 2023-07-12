package com.example.worly.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.worly.model.Answer;
import com.example.worly.model.Question;
import com.example.worly.model.Question_Attempt;
import com.example.worly.model.QuizTemplate;
import com.example.worly.model.Quiz_Attempt;
import com.example.worly.repository.AnswerRepository;
import com.example.worly.repository.EnvironmentalistRepository;
import com.example.worly.repository.QuestionAttemptRepository;
import com.example.worly.repository.QuestionRepository;
import com.example.worly.repository.QuizAttemptRepository;
import com.example.worly.repository.QuizTemplateRepository;
import com.example.worly.repository.RecommendationRepository;
import com.example.worly.repository.UserRepository;
import com.example.worly.rowmodel.QuestionAttemptRowModel;
import com.example.worly.rowmodel.QuizAttemptRowModel;
import com.example.worly.rowmodel.QuizTempQuestionRowModel;
import com.example.worly.user.Environmentalist;
import com.example.worly.user.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class QuizAttemptService {

	private int baseCarbonFootprint = 2000;

	@Autowired
	private QuizTemplateRepository quizTempRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RecommendationRepository recomRepository;

	@Autowired
	private QuizLoaderService qls;

	@Autowired
	private QuizTemplateService quizTempService;

	@Autowired
	private QuizAttemptRepository quizAttemptRepos;

	@Autowired
	private QuestionAttemptRepository questionAttemptRepos;

	@Autowired
	private EnvironmentalistRepository envRepos;

	public QuizAttemptRowModel generateQuizAttemptForm(List<QuizTempQuestionRowModel> questions, HttpSession session) {
		// Makes an array of question attempts that is the same size as the number of questions in its quiz template
		// Also has linked question object - but a lot of null values
		
		System.out.println("");
		long startTime = System.currentTimeMillis();
		
		List<QuestionAttemptRowModel> questionAttemptList = new ArrayList<>();
		QuestionAttemptRowModel tempQuestionAttempt;
		Long tempQuestionID;

		
		// function that takes time
		// this function should approximately halve the time at the Quiz Attempt controller once a quiz is chosen
		// List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(quizTemplate.getQuizTemplateID());
		
		// loops for each question row model and creates a question attempt
		for (int i=0; i!=questions.size(); i++) {
			tempQuestionID = questionRepository.findByQuestionID(questions.get(i).getQuestionID()).get().getQuestionID();
			tempQuestionAttempt = new QuestionAttemptRowModel(0, tempQuestionID); // NEED TO ADD QUIZ_ATTEMPT OBJECT AND ADD ANSWER AFTER
			questionAttemptList.add(tempQuestionAttempt);
		}

		QuizAttemptRowModel quizAttemptForm =
			new QuizAttemptRowModel(
			null, // No need for quiz template ID field as this does not appear in the form
			null, // No need to add current date getter
			null, // No need to add current time getter
			baseCarbonFootprint,
			null, // No need for a unique identifier as this isn't a table
			questionAttemptList);

		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime) ; 
		duration = duration/ 1000;
		System.out.println("Time taken for generation of quiz form: "+ duration);
		
		return quizAttemptForm;
	}

	public QuizAttemptRowModel addDetailsToQuizAttemptForm
	(QuizAttemptRowModel quizAttemptForm,QuizTemplate quizTemplate) {

		List<QuestionAttemptRowModel> questionAttemptList = new ArrayList<>();
		QuestionAttemptRowModel tempQuestionAttempt;
		Long tempQuestionID;
		String tempAnswerText;

		// adds the question id and the answer text for each question
		List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(quizTemplate.getQuizTemplateID());
		for (int i=0; i!=questions.size(); i++) {
			tempQuestionID = questionRepository.findByQuestionID(questions.get(i).getQuestionID()).get().getQuestionID();
			tempAnswerText = answerRepository.findByAnswerID(quizAttemptForm.getQuestionAttemptList().get(i).getAnswerID())
					.get().getAnswerText();
			quizAttemptForm.getQuestionAttemptList().get(i).setQuestionID(tempQuestionID);
			quizAttemptForm.getQuestionAttemptList().get(i).setAnswerText(tempAnswerText);
		}

		return quizAttemptForm;
	}

	public int calculateScore(HttpSession session, QuizAttemptRowModel quizAttemptForm, QuizTemplate quizTemplate) {
		int totalScore = baseCarbonFootprint;
		List<QuestionAttemptRowModel> userInputList = quizAttemptForm.getQuestionAttemptList();
		Answer tempAnswer;

		// Calculates the score by iterating through the form (each answer in the list)
		for (int i=0; i!=quizAttemptForm.getQuestionAttemptList().size();i++) {
			tempAnswer = answerRepository.findByAnswerID(userInputList.get(i).getAnswerID()).get();
			totalScore = totalScore+tempAnswer.getCFPValue();
		}

		// Only save the score if user is logged in
		if (getUserLoggedIn(session)) {
			System.out.println("User is logged in, saving the stats");
			saveQuizAttempt(session, quizAttemptForm, quizTemplate, totalScore);
			updateEnvironmentalistStats(session, totalScore);
		}

		return totalScore;
	}

	public int getTotalQuestions(QuizAttemptRowModel quizAttemptForm) {
		return quizAttemptForm.getQuestionAttemptList().size();
	}

	public int getTotalCorrectAnswers(QuizAttemptRowModel quizAttemptForm) {
		int rightAnswers = 0;
		Answer tempAnswer;
		List<QuestionAttemptRowModel> userInputList = quizAttemptForm.getQuestionAttemptList();

		for (int i=0; i!=quizAttemptForm.getQuestionAttemptList().size();i++) {
			tempAnswer = answerRepository.findByAnswerID(userInputList.get(i).getAnswerID()).get();
			if (tempAnswer.isFlag())
				rightAnswers++;
		}

		return rightAnswers;
	}

	public boolean getUserLoggedIn(HttpSession session) {
		if (session.getAttribute("loggedIn")==null)
			return false;
		
		return (boolean) session.getAttribute("loggedIn");
	}

	public long getLoggedInUserID(HttpSession session ) {
		String targetEmail = (String) session.getAttribute("email");
		return userRepository.findByEmail(targetEmail).get().getUserID();
	}

	public String getLoggedInUserEmail(HttpSession session ) {
		return (String) session.getAttribute("email");
	}

	// we should save a quiz attempt when calculating the score
	public void saveQuizAttempt(HttpSession session, QuizAttemptRowModel quizAttemptForm, QuizTemplate quizTemplate, int quizScore) {
		System.out.println("Saving quiz attempt");


		Date currentDate = new Date();

		User targetUser = userRepository.findByEmail(getLoggedInUserEmail(session)).get();
		Environmentalist targetEnvironmentalist = envRepos.findByUser(targetUser);

		Quiz_Attempt newQuizAttempt = new Quiz_Attempt(
				targetEnvironmentalist,
				quizTemplate,
				currentDate,
				null, // probably dont need time since date already contains time
				quizScore);

		quizAttemptRepos.save(newQuizAttempt);

		// creating the question attempts (Based off the quizAttemptRowModel form) for the quiz attempt
		List<QuestionAttemptRowModel> questionAttemptList = quizAttemptForm.getQuestionAttemptList();
		QuestionAttemptRowModel tempQuestionAttempt;
		Question_Attempt newQuestionAttempt;
		Question targetQuestion;
		Answer targetAnswer;
		for (int i=0; i!=questionAttemptList.size();i++)
		{
			tempQuestionAttempt = questionAttemptList.get(i);
			targetQuestion = questionRepository.findByQuestionID(tempQuestionAttempt.getQuestionID()).get();
			targetAnswer = answerRepository.findByAnswerID(tempQuestionAttempt.getAnswerID()).get();
			questionAttemptRepos.save(new Question_Attempt(newQuizAttempt, targetQuestion, targetAnswer));
		}
	}

	public void updateEnvironmentalistStats(HttpSession session, int score) {
		System.out.println("Updating environmentalist stats");
		User targetUser = userRepository.findByEmail(getLoggedInUserEmail(session)).get();
		Environmentalist targetEnvironmentalist = envRepos.findByUser(targetUser);

		int newQuizzesTaken = 1 + targetEnvironmentalist.getQuizzesTaken();
		int newTotalCFPScore = targetEnvironmentalist.getTotalCFPScore() + score;
		int newAvgCFPScore = newTotalCFPScore / newQuizzesTaken;


		targetEnvironmentalist.setQuizzesTaken(newQuizzesTaken);
		targetEnvironmentalist.setTotalCFPScore(newTotalCFPScore);
		targetEnvironmentalist.setAvgCFPScore(newAvgCFPScore);
		envRepos.save(targetEnvironmentalist);
	}

}
