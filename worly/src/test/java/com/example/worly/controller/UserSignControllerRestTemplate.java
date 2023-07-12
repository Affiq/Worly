package com.example.worly.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class UserSignControllerRestTemplate {


	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testHelloController(){
		assertNotNull(restTemplate.getForObject("http://localhost:8080/api/sign", String.class));

	}


}