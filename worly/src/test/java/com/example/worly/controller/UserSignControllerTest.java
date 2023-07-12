package com.example.worly.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserSignControllerTest {

	@Autowired
	MockMvc mockMvcTest;




	@Test
	public void testReturnContentOfUserSign() throws Exception {
		MvcResult mvcResultTest;
		mvcResultTest = mockMvcTest.perform(get("/api/sign")).andDo(print()).andExpect(status().isOk()).andReturn();
		String expectedContent= "text/html;charset=UTF-8";
		assertEquals(mvcResultTest.getResponse().getContentType(),expectedContent);
	}

	@Test
	public void testReturnContentOfUserSignin() throws Exception {
		MvcResult mvcResultTest;
		mvcResultTest = mockMvcTest.perform(get("/api/sign/in")).andDo(print()).andExpect(status().isOk()).andReturn();
		String expectedContent= "text/html;charset=UTF-8";
		assertEquals(mvcResultTest.getResponse().getContentType(),expectedContent);
	}

	@Test
	public void testReturnContentOfUserForget() throws Exception {
		MvcResult mvcResultTest;
		mvcResultTest = mockMvcTest.perform(get("/api/sign/forget")).andDo(print()).andExpect(status().isOk()).andReturn();
		String expectedContent= "text/html;charset=UTF-8";
		assertEquals(mvcResultTest.getResponse().getContentType(),expectedContent);
	}





}
