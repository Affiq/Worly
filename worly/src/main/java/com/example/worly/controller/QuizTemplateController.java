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
import com.example.worly.request.Message;
import com.example.worly.request.NewAnswerRequest;
import com.example.worly.request.QuestionRequest;
import com.example.worly.request.QuizTemplateRequest;
import com.example.worly.rowmodel.QuizTempAnswerRowModel;
import com.example.worly.rowmodel.QuizTempQuestionRowModel;
import com.example.worly.rowmodel.QuizTemplateRowModel;
import com.example.worly.service.QuizTemplateService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/quiztemplates")
@AllArgsConstructor
public class QuizTemplateController {

	@Autowired
	private QuizTemplateService quizTempService;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	private String errorpagename = "erm";
	private String msgText = "Invalid authority - you must be an admin to access this page";
	
	// GET api/quiztemplates
	@GetMapping("")
    public String showForm(Model model, HttpSession session) {
		Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
		
		if (validateAdmin(session)==false) 
			return errorpagename;
			
        QuizTemplateRequest quiztemprequest = new QuizTemplateRequest();
        model.addAttribute("request", quiztemprequest);
        
        List<QuizTemplateRowModel> qtRowModelList = quizTempService.getQuizTemplateRowModels();
        System.out.println(qtRowModelList.size());
        model.addAttribute("qtRowModelList", qtRowModelList);
          		
        return "quiz_template";
    }
    
    // POST api/quiztemplate/    
    @PostMapping("")
    @ResponseBody
	public String submitForm(Model model, @ModelAttribute("request") QuizTemplate request, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	if (quizTempService.quizNameExists(request.getQuizName())) {
    		return "not added";
    	}
    	
    	quizTempService.addQuizTemplate(request.getQuizName());
    	return "New quiz template added";
	}
    
    @PostMapping("releasequiz")
    @ResponseBody
	public String releaseQuizTemplate(Model model, @RequestParam("qtid") int qtid, HttpSession session)
	{
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
		
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	quizTempService.releaseQuizTemplate(qtid);
    	return "Quiz is now released and non editable";
	}
    
    // GET api/quiztemplate/view?=    
    @GetMapping(path = "view")
    public String viewQuizTemplate(Model model, @RequestParam("qtid") int qtid, HttpSession session) {
    	
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	if (quizTempService.quizTemplateExists(qtid))
    	{
    		if (quizTempService.isEditable(qtid))
    			return qtid+" should be editable"; // redirection to the edit page is needed here
    		else {
    			QuizTemplate quizTemplate = quizTempService.getQuizTemplate(qtid).get();
    			model.addAttribute("quizTemplate", quizTemplate);
    	        
    			List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(qtid);
    			model.addAttribute("questions",questions);
    			
            	return "view_quiz";
    		}
    	}
    	else
    		return "this quiz template does not exist"; // redirect to the quiz template page is needed here
    }
    
    
    
    // GET api/quiztemplate/editquiz?=
    @GetMapping(path = "editquiz")
    public String editQuizTemplate(Model model, @RequestParam("qtid") int qtid, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
     	if (quizTempService.quizTemplateExists(qtid)) {
    		if (quizTempService.isEditable(qtid)) {
    			QuizTemplate quizTemplate = quizTempService.getQuizTemplate(qtid).get();
    			QuestionRequest questionRequest = new QuestionRequest();
    			List<QuizTempQuestionRowModel> questions = quizTempService.getQTQuestionRowModels(qtid);
    			
    			model.addAttribute("quizTemplate", quizTemplate);
    			model.addAttribute("questionRequest", questionRequest);
    			model.addAttribute("questions",questions);
    			
            	return "edit_quiz";
    		}
    		else
    			return qtid+" is not editable"; // redirection to quiz template is needed here
    	}
    	else
    		return "this quiz template does not exist";   // redirection to quiz template page is needed here}
    	}
    
    // POST api/quiztemplate/editquiz?=
    @PostMapping("editquiz")
    @ResponseBody
	public String addQuestion(Model model, @ModelAttribute("questionRequest") QuestionRequest request, 
			@RequestParam("qtid") int qtid, HttpSession session)
	{
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	QuizTemplate quizTemp = quizTempService.getQuizTemplate(qtid).get();
    	quizTempService.addQuestionToQuizTemplate(quizTemp, request.getQuestionText(), request.getCategory());
    	return "Question added";
	}
    
    
    // GET api/quiztemplate/editquestion?=
    @GetMapping(path = "editquestion")
    public String editQuestion(Model model, @RequestParam("questionid") long questionid, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	if (quizTempService.questionExists(questionid))
    	{
    		// get the question, its parent quiz and the parent quiz ID
			Question question = questionRepository.findByQuestionID(questionid).get();
    		QuizTemplate quizTemplate = questionRepository.findByQuestionID(questionid).get().getQuiz_Template();
    		long quizTempId = quizTemplate.getQuizTemplateID();
    				
        	quizTempService.isEditable(quizTempId);
    		if (quizTempService.isEditable(quizTempId))
    		{
    			QuizTempQuestionRowModel questionModel = new QuizTempQuestionRowModel();
    			questionModel.setAnswers(quizTempService.getQTAnswerRowModels(questionid)); // Fetch the list of answers into the question model
    			NewAnswerRequest newAnswerRequest = new NewAnswerRequest();
    			
    			model.addAttribute("newAnswerRequest", newAnswerRequest);
    			model.addAttribute("question", question);
    			model.addAttribute("questionModel", questionModel);
    			return "edit_question";
    		}
    		else
    			return "this question is no longer editable";
    	}
    	else
    		return "this question doesn't exist!";
    }
    
    @PostMapping("editquestion")
    @ResponseBody
    public String updateQuestion(Model model, @ModelAttribute("question") Question question, 
    		@RequestParam("questionid") long questionid, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
		quizTempService.updateQuestion(questionid, question);
    	return "Question edited";
    }
    
    @PostMapping("deletequestion")
    @ResponseBody
    public String deleteQuestion(Model model, @RequestParam("questionid") long questionid, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
		quizTempService.deleteQuestion(questionid);
    	return "Question deleted";
    	
    }
    
    // needs some checking! cannot add answer to released question.
    @PostMapping("addanswer")
    @ResponseBody
	public String addAnswer(Model model, @ModelAttribute("questionRequest") NewAnswerRequest request, 
			@RequestParam("questionid") long questionid, HttpSession session) 
    {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	Question question = quizTempService.getQuestion(questionid);;
    	quizTempService.addAnswerToQuestion(question, request.getAnswerText(), 
    			request.getCfpValue(), request.isFlag());
    	return "Answer added";
	}
    
    @PostMapping("deleteanswer")
    @ResponseBody
    public String deleteAnswer(Model model, @RequestParam("answerid") long answerid, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	quizTempService.deleteAnswer(answerid);
    	return "Answer and its recommendations deleted";
    }
    
    // needs some checking! cannot add recommendation to a released quiz (and to the ones below)
    @PostMapping("addrecommendation")
    @ResponseBody
    public String addRecommendation(Model model, @ModelAttribute("questionRequest") NewAnswerRequest request, 
    		@RequestParam("answerid") long answerid, HttpSession session) 
    {    	
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	Answer answer = quizTempService.getAnswer(answerid);
    	quizTempService.addRecommendationToAnswer(answer, "New recommendation - edit this");
    	return "Recommendation added";
    }
    
    @PostMapping("deleterecommendation")
    @ResponseBody
    public String deleteRecommendation(Model model, @RequestParam("recommendationid") long recommendationid,
    		HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    	quizTempService.deleteRecommendation(recommendationid);
    	return "Recommendation deleted";
    }
    
    @PostMapping("editanswer")
    @ResponseBody
    public String editAnswer(Model model, @RequestParam("answerid") long answerid, 
    		@ModelAttribute("questionModel") QuizTempQuestionRowModel questionModel, HttpSession session) {
    	Message msgrq = new Message();
		msgrq.setMsg(msgText);
		model.addAttribute("msg", msgrq);
    	
    	if (validateAdmin(session)==false)
			return errorpagename;
    	
    		quizTempService.updateAnswerAndRecommendations(answerid, questionModel);
    	return "Answer and recommendations updated (not yet implemented)";
    }
    
    private boolean validateAdmin(HttpSession session) {
    	
    	if (session.getAttribute("admin")==null)
    		return false;
    	if (session.getAttribute("admin")=="false")
    		return false;
     	if (session.getAttribute("admin")=="true")
    		return true;
    	
    	return false;
    }
    
}
