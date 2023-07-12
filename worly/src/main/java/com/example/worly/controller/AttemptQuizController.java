package com.example.worly.controller;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.worly.model.QuizTemplate;
import com.example.worly.repository.QuestionRepository;
import com.example.worly.request.CheckAnswerRequest;
import com.example.worly.request.QuestionRequest;
import com.example.worly.request.QuizTemplateRequest;
import com.example.worly.rowmodel.QuizTempQuestionRowModel;
import com.example.worly.rowmodel.QuizTemplateRowModel;
import com.example.worly.service.QuizTemplateService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/attempt")
@AllArgsConstructor
public class AttemptQuizController {

	@Autowired
	private QuizTemplateService quizTempService;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	// GET api/attempt
	@GetMapping("")
    public String showForm(Model model) {
        QuizTemplateRequest quiztemprequest = new QuizTemplateRequest();
        model.addAttribute("request", quiztemprequest);
        
        List<QuizTemplateRowModel> qtRowModelList = quizTempService.getAttemptQuizRowModels();
        System.out.println(qtRowModelList.size());
        model.addAttribute("qtRowModelList", qtRowModelList);
          		
        return "attempt_quizform";
    }
	
	@GetMapping(path= "quiz")
	public String submitForm(Model model, @RequestParam("qtid") int qtid)  {
		QuizTemplate quizTemplate = quizTempService.getQuizTemplate(qtid).get();
		QuestionRequest questionRequest = new QuestionRequest();
		
		CheckAnswerRequest answerRequest =new CheckAnswerRequest();
		List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(qtid);
		
		model.addAttribute("quizTemplate", quizTemplate);
		model.addAttribute("questionRequest", questionRequest);
		model.addAttribute("questions",questions);
	
		// need to see if this is correct.
		model.addAttribute("checkAnswers", answerRequest);
		System.out.println(answerRequest.getAnswerText());
    	return "attempt_quiz";
	}
	
	@PostMapping()
	@ResponseBody
	public String quiz() {
		
		return "2,ol";
	}
}
