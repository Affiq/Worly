package com.example.worly.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.worly.model.Answer;
import com.example.worly.model.Question;
import com.example.worly.model.QuizTemplate;
import com.example.worly.model.Recommendation;
import com.example.worly.repository.AnswerRepository;
import com.example.worly.repository.QuestionRepository;
import com.example.worly.repository.QuizTemplateRepository;
import com.example.worly.repository.RecommendationRepository;
import com.example.worly.repository.UserRepository;
import com.example.worly.rowmodel.QuizTempAnswerRowModel;
import com.example.worly.rowmodel.QuizTempQuestionRowModel;
import com.example.worly.rowmodel.QuizTempRecomRowModel;
import com.example.worly.rowmodel.QuizTemplateRowModel;
import com.example.worly.token.VerificationToken;
import com.example.worly.user.User;

import lombok.AllArgsConstructor;

//class to find users when we are trying to login 
@Service
@AllArgsConstructor
public class QuizTemplateService{

	@Autowired
	private  QuizTemplateRepository quizTempRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private RecommendationRepository recomRepository;

	@Autowired
	private QuizLoaderService qls;

	public void releaseQuizTemplate(long qtid) {
		QuizTemplate quizTemp = getQuizTemplate(qtid).get();
		quizTemp.setReleased(true);
		quizTempRepository.save(quizTemp);
	}

	public void updateQuestion(long questionID, Question question) {
		Question questionToUpdate = qls.getQuestion(questionID);
		questionToUpdate.setQuestionText(question.getQuestionText());
		questionToUpdate.setCategory(question.getCategory());
		questionRepository.save(questionToUpdate);
	}

	public void updateAnswerAndRecommendations(long answerid, QuizTempQuestionRowModel questionRowModel) {

		// statement to select correct answer in the question row model - the one containing the answerid parameter
		int listSize = questionRowModel.getAnswers().size();
		QuizTempAnswerRowModel inputAnswer = null;
		for (int index = 0; index != listSize; index++)
		{
			if (questionRowModel.getAnswers().get(index).getAnswerID() == answerid);
			inputAnswer = questionRowModel.getAnswers().get(index);
		}

		Answer answerToUpdate = qls.getAnswer(answerid);

		// statement to update answer
		answerToUpdate.setAnswerText(inputAnswer.getAnswerText());
		answerToUpdate.setCFPValue(inputAnswer.getCfpValue());
		answerToUpdate.setFlag(inputAnswer.isFlag());
		answerRepository.save(answerToUpdate);

		// statement to update answer recommendations
		try {
			List<QuizTempRecomRowModel> inputRecoms = inputAnswer.getRecommendations();
			listSize = inputRecoms.size();
			for (int index = 0; index != listSize; index++)
			{
				long targetRecomID = inputRecoms.get(index).getRecommendationID();
				Recommendation targetRecom = recomRepository.findByRecommendationID(targetRecomID).get();
				targetRecom.setRecommendationText(inputRecoms.get(index).getRecommendationText());
				recomRepository.save(targetRecom);
			}
		}
		catch (Exception e) {
			System.out.println("No recommendations!");
		}
	}

	public void addQuestionToQuizTemplate(QuizTemplate quizTemplate, String questionText, String category) {
		questionRepository.save(new Question(quizTemplate, questionText, category));
	}

	public void addAnswerToQuestion(Question question, String answerText, int cfpValue, boolean flag) {
		answerRepository.save(new Answer(question, answerText, cfpValue, flag));
	}

	public void addRecommendationToAnswer(Answer answer, String recomText) {
		recomRepository.save(new Recommendation(answer, recomText));
	}

	public void deleteRecommendation(long recommendationID) {
		recomRepository.deleteByRecommendationID(recommendationID);
	}

	public void deleteAnswer(long answerID) {
		Answer answer = answerRepository.findByAnswerID(answerID).get();
		recomRepository.deleteAllByAnswer(answer);
		answerRepository.deleteByAnswerID(answerID);
	}

	public void deleteQuestion(long questionID) {
		Question question = questionRepository.findByQuestionID(questionID).get();
		List<Answer> answers = answerRepository.findAllByQuestion(question);

		for (int index = 0; index != answers.size(); index++) {
			deleteAnswer(answers.get(index).getAnswerID());
		}

		questionRepository.deleteByQuestionID(questionID);
	}

	public Question getQuestion(long questionID) {
		return qls.getQuestion(questionID);
	}

	public Answer getAnswer(long answerID) {
		return qls.getAnswer(answerID);
	}

	// checks if quiz template exists
	public boolean quizTemplateExists(long quizID) {
		return qls.quizTemplateExists(quizID);
	}


	public boolean questionExists(long questionID) {
		return qls.questionExists(questionID);
	}

	// checks if quiz template is editable or view only
	public boolean isEditable(long quizID) {
		if (getQuizTemplate(quizID).get().isReleased() == false)
			return true;
		else
			return false;
	}


	// gets quiz template using quiz id
	public Optional<QuizTemplate> getQuizTemplate(long quizID) {
		return quizTempRepository.findByQuizTemplateID(quizID);
	}


	// gets all quiz templates in database
	public List<QuizTemplate> getAllQuizTemplates() {
		return quizTempRepository.findAll();
	}


	// adds new quiz template to database
	public void addQuizTemplate(String quizName) {
		quizTempRepository.save(new QuizTemplate(quizName));
	}
	
	// Converts a list of QuizTemplates to a list of QuizTemplateRowModels
	// Returns a list of ButtonTextAndValue which are (ButtonText, ButtonValue) --> (String,String)
	public List<QuizTemplateRowModel> getQuizTemplateRowModels() {
		
		System.out.println("");
		long startTime = System.currentTimeMillis();
		
		List<QuizTemplate> QTList = getAllQuizTemplates();
		List<QuizTemplateRowModel> valuesList = new ArrayList();
		int listSize = QTList.size();
		int index = 0;
		QuizTemplate currentQT;
		QuizTemplateRowModel temp = null;

		while (index != listSize)
		{
			currentQT = QTList.get(index);
			if (currentQT.isReleased()) // if isReleased
			{
				temp = new QuizTemplateRowModel(currentQT.getQuizName(), currentQT.getQuizTemplateID(),
						"View", "quiztemplates/view?qtid="+currentQT.getQuizTemplateID());
			}
			else // if unreleased
			{
				temp = new QuizTemplateRowModel(currentQT.getQuizName(), currentQT.getQuizTemplateID(),
						"Edit", "quiztemplates/editquiz?qtid="+currentQT.getQuizTemplateID());
			}
			valuesList.add(temp);
			index++;
		}
		
		long endTime = System.currentTimeMillis();
		double duration = (endTime - startTime) ; 
		duration = duration/ 1000;
		System.out.println("Time taken for quizTempRowModel: "+ duration);
		
		return valuesList;
	}

	// Might not even be needed - recommendations and its rowmodels have matching attributes...
	// Converts list of Recommendations to list of QuizTempRecomRowModels...
	public List<QuizTempRecomRowModel> getQTRecomRowModels(long answerID) {
		List<Recommendation> recommendations = qls.getAnswerRecommendations(answerID);
		List<QuizTempRecomRowModel> rowModelList = new ArrayList();
		int listSize = recommendations.size();
		int index = 0;
		Recommendation currentRecom;
		QuizTempRecomRowModel currentRowModel;

		while (index != listSize) {
			currentRecom = recommendations.get(index);
			currentRowModel = new QuizTempRecomRowModel(
					currentRecom.getRecommendationText(), //RowModels recommendation text
					currentRecom.getRecommendationID()); //RecommendationID
			rowModelList.add(currentRowModel);
			index++;
		}
		return rowModelList;
	}

	public List<QuizTempAnswerRowModel> getQTAnswerRowModels(long questionID) {
		List<Answer> answers = qls.getQuestionAnswers(questionID);
		List<QuizTempAnswerRowModel> rowModelList = new ArrayList();
		int listSize = answers.size();
		int index = 0;
		Answer currentAnswer;
		QuizTempAnswerRowModel currentRowModel;	

		while (index != listSize) {
			currentAnswer = answers.get(index);
			currentRowModel = new QuizTempAnswerRowModel(
					currentAnswer.getAnswerText(),
					currentAnswer.getCFPValue(),
					currentAnswer.isFlag(),
					currentAnswer.getAnswerID(),
					getQTRecomRowModels(currentAnswer.getAnswerID()));
			rowModelList.add(currentRowModel);
			index++;
		}

		return rowModelList;
	}

	
	
	
	// This is an optimised version of the function below
	// It will involve a lot of raw processing in exchange for a lower number of repeated queries
	public List<QuizTempQuestionRowModel> getQTQuestionRowModels(long quizTempID) {
		
		// Get a list of answers and recommendations to avoid multiple queries.
		List<Answer> answers = qls.getAllAnswers();
		int answerListSize = answers.size();
		List<Recommendation> recommendations = qls.getAllRecommendations();
		int recomListSize = recommendations.size();
		
		// These are the row models we will return
		List<QuizTempQuestionRowModel> questionRowModelList = new ArrayList();
		
		// These are the question objects we queried and will loop through
		List<Question> questions = qls.getQuizQuestions(quizTempID);
		int questionListSize = questions.size();
		
		// Temporary variables used as iterators
		Question currentQuestion;
		QuizTempQuestionRowModel currentQuestionRowModel;
		long currentQuestionID;
		
		Answer currentAnswer;
		QuizTempAnswerRowModel currentAnswerRowModel;
		long currentAnswerID;
		
		Recommendation currentRecom;
		QuizTempRecomRowModel currentRecomRowModel;

		
		// We create a bunch of QuestionRowModel objects into the list
		// Only in this version we do not add the List of AnswerRowModel into the QuestionRowModel
		for (int Qindex=0; Qindex != questionListSize; Qindex++) {
			// current question and ID obtained
			currentQuestion = questions.get(Qindex);
			currentQuestionID = currentQuestion.getQuestionID();


			// Block of code to get Answer row lists for each question...
			List<QuizTempAnswerRowModel> answerRowModelList = new ArrayList<QuizTempAnswerRowModel>();
			for (int Aindex=0; Aindex!=answerListSize; Aindex++) {

				currentAnswer = answers.get(Aindex);
				if (currentAnswer.getQuestion().getQuestionID()==currentQuestionID) { // if the answer has a matching questionid
				currentAnswerID = currentAnswer.getAnswerID();
				
				// Block of code to get Recom row lists for each answer
				List<QuizTempRecomRowModel> recomRowModelList = new ArrayList<QuizTempRecomRowModel>();
				for (int Rindex=0; Rindex!=recomListSize; Rindex++) {
					
					currentRecom = recommendations.get(Rindex);
					if (currentRecom.getAnswer().getAnswerID() == currentAnswerID) {
						
						// create recom row model
						currentRecomRowModel = new QuizTempRecomRowModel(
						currentRecom.getRecommendationText(),
						currentRecom.getRecommendationID());
						
						recomRowModelList.add(currentRecomRowModel);
					}
				}
				// create answer row model
				currentAnswerRowModel = new QuizTempAnswerRowModel(
						currentAnswer.getAnswerText(),
						currentAnswer.getCFPValue(),
						currentAnswer.isFlag(),
						currentAnswer.getAnswerID(),
						recomRowModelList); // This version assigns an empty list of RecomRowModel for now
						// and add to list
						answerRowModelList.add(currentAnswerRowModel);
			
				}
			}
			
			currentQuestionRowModel = new QuizTempQuestionRowModel(
					currentQuestion.getQuestionText(),
					currentQuestionID,
					currentQuestion.getCategory(),
					answerRowModelList); // This version assigns an empty list of AnswerRowModel
			
			questionRowModelList.add(currentQuestionRowModel); // Add current question row model to the list
			Qindex++;
		}
		
		return questionRowModelList;
	}
	
	
	
	
	
	
	
	
	// Used to get the QuestionRowModels... --> Each QuestionRowModel contains a list of AnswerRowModel,
	// This is the old unused version, took too much time
	// This is due to a large number of queries
	// Num Of queries is ~ num of questions x num of answers x num of recommendations
	// calculated through a different method
	public List<QuizTempQuestionRowModel> unoptimisedGetQTQuestionRowModels(long quizTempID) {
		List<Question> questions = qls.getQuizQuestions(quizTempID);
		List<QuizTempQuestionRowModel> rowModelList = new ArrayList();
		int listSize = questions.size();
		int index = 0;
		Question currentQuestion;
		QuizTempQuestionRowModel currentRowModel;

		while (index != listSize) {
			currentQuestion = questions.get(index);
			currentRowModel = new QuizTempQuestionRowModel(
					currentQuestion.getQuestionText(),
					currentQuestion.getQuestionID(),
					currentQuestion.getCategory(),
					getQTAnswerRowModels(currentQuestion.getQuestionID())); // Get the answer row models
			rowModelList.add(currentRowModel); // Add current question row model to the list
			index++;
		}
		return rowModelList;
	}

	
	// Converts a list of QuizTemplates to a list of QuizTemplateRowModels
	// Returns a list of ButtonTextAndValue which are (ButtonText, ButtonValue) --> (String,String)
	public List<QuizTemplateRowModel> getAttemptQuizRowModels(){
		List<QuizTemplate> QTList = getAllQuizTemplates();
		List<QuizTemplateRowModel> valuesList = new ArrayList();
		int listSize = QTList.size();
		int index = 0;
		QuizTemplate currentQT;
		QuizTemplateRowModel temp = null;

		while (index != listSize)
		{
			currentQT = QTList.get(index);
			temp = new QuizTemplateRowModel(currentQT.getQuizName(), currentQT.getQuizTemplateID(),
					"Attempt", "attempt/quiz?qtid="+currentQT.getQuizTemplateID());
			valuesList.add(temp);
			index++;
		}
		return valuesList;
	}

	public List<QuizTemplate> getAvailableQuizzes() {
		return quizTempRepository.findAllByReleased(true);
	}	
	
	public boolean quizNameExists(String quizName) {
		try {
			QuizTemplate targetQuiz = quizTempRepository.findByQuizName(quizName).get();
			
			if (targetQuiz.getQuizName().equals(quizName))
				return true;
			else
				return false;
			
		} catch (Exception e) {
			System.out.println(e);
			return false;}
	}
}
