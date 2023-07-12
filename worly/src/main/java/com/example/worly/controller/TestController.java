package com.example.worly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.worly.register.RegistrationRequest;


import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/test")
@AllArgsConstructor
public class TestController {
	
	
	
	
	@GetMapping("/recommendation")
	public String recommendation(){
		return "Recommendation";
	}
	
	
	
	
	@GetMapping("/feedback")
	public String feedback(){
		return "Feedback";
	}
	
	@GetMapping("/user")
	public String user() {
		return "profile";
	}

	@GetMapping("/results")
	public String results() {
		return "result";
	}
	@GetMapping("/quiz")
	public String quiz() {
		return "Quiz_Index_F";
	}
}
