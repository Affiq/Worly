package com.example.worly.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.worly.model.Answer;
import com.example.worly.model.Question;
import com.example.worly.model.QuizTemplate;
import com.example.worly.register.RegistrationRequest;
import com.example.worly.repository.QuestionRepository;

import com.example.worly.request.NewAnswerRequest;
import com.example.worly.request.QuestionRequest;
import com.example.worly.request.QuizTemplateRequest;
import com.example.worly.rowmodel.QuizAttemptRowModel;
import com.example.worly.rowmodel.QuizTempAnswerRowModel;
import com.example.worly.rowmodel.QuizTempQuestionRowModel;
import com.example.worly.rowmodel.QuizTemplateRowModel;
import com.example.worly.service.QuizAttemptService;
import com.example.worly.service.QuizTemplateService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/attemptquiz")
@AllArgsConstructor
public class QuizAttemptController {

	@Autowired
	private QuizTemplateService quizTempService;
	
	@Autowired
	private QuizAttemptService quizAttemptService;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	// GET api/attemptquiz/choosequiz
	// Returns the choose quiz page
	@GetMapping("choosequiz")
    public String showAvailableQuizzes(Model model) {
        List<QuizTemplate> availableQuizList = quizTempService.getAvailableQuizzes();
        model.addAttribute("quizList", availableQuizList);
          		
        return "view_available_quizzes";
    }
    
    // POST api/attemptquiz/attempt?qtid=
	// Returns a page where the user can attempt the quiz
    @PostMapping(path = "attempt")
	public String getQuiz(Model model, @RequestParam("qtid") int qtid, HttpSession session) {
    	
       	if (quizTempService.quizTemplateExists(qtid))
    	{
    		if (quizTempService.isEditable(qtid))
    			return qtid+" should not be able to answer as it is not released"; // cannot answer this question as it is not released
    		else {
    			
    			long startTime = System.currentTimeMillis();
    			
    			QuizTemplate quizTemplate = quizTempService.getQuizTemplate(qtid).get();
    			model.addAttribute("quizTemplate", quizTemplate);
    	        
    			List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(qtid);
    			model.addAttribute("questions",questions);
    			
       			QuizAttemptRowModel quizAttemptForm = quizAttemptService.generateQuizAttemptForm(questions, session);
    			model.addAttribute("quizAttemptForm", quizAttemptForm);
    			    		
    			long endTime = System.currentTimeMillis();
    			double duration = (endTime - startTime) ; 
    			duration = duration/ 1000;
    			
    			System.out.println("Time taken for quiz controller: "+ duration);

            	return "attempt_quiz";
    		}
    	}
    	else 
    		return "cannot answer quiz - it does not exist"; // redirect to the quiz template page is needed here
	}
    

    @PostMapping(path = "submit")
    
  	public String submitQuiz(Model model, @ModelAttribute("quizAttemptForm") QuizAttemptRowModel quizAttempt, 
  			HttpSession session, @RequestParam("qtid") int qtid) {
    
    	// Adding 'missing' questionID details to quizAttempt parameter (form) 
    	QuizTemplate targetQuizTemplate = quizTempService.getQuizTemplate(qtid).get();
    	quizAttemptService.addDetailsToQuizAttemptForm(quizAttempt, targetQuizTemplate);
    	
    	int totalQuestions = quizAttemptService.getTotalQuestions(quizAttempt);
    	model.addAttribute("totalQuestions", totalQuestions);
    	
    	int totalCorrectAnswers  =	quizAttemptService.getTotalCorrectAnswers(quizAttempt);
    	model.addAttribute("totalCorrectAnswers", totalCorrectAnswers);
    	
    	int score = quizAttemptService.calculateScore(session, quizAttempt, targetQuizTemplate);
    	model.addAttribute("score",score);
    	
    	QuizTemplate quizTemplate = targetQuizTemplate;
		model.addAttribute("quizTemplate", quizTemplate);
		        
		List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(qtid);
		
		model.addAttribute("questions",questions);
		
    	return "quiz_results";
    }
}
