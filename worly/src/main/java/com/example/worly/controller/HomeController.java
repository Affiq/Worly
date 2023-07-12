package com.example.worly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/home")
@AllArgsConstructor
public class HomeController {
	@GetMapping()
	public String home(){
		return "Home";
	}
}
