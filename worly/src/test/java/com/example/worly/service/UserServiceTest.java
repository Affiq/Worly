package com.example.worly.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.worly.model.QuizTemplate;
import com.example.worly.repository.QuizTemplateRepository;
import com.example.worly.repository.UserRepository;
import com.example.worly.user.User;
import com.example.worly.user.UserRole;

// Test to see if quiz templates and respective details are generated
@SpringBootTest
class UserServiceTest {

	@Autowired
	UserService us;
	
	@Autowired
	UserRepository userRep;
	
	@Test
	void testUserServiceRegistration() {
		String newEmail = "newguy@gmail.com";
		User newUser = new User(newEmail,"password","Environmentalist","Newman","Guy");
		// register the new user
		us.registerUser(newUser);
		
		User registeredUser = us.getUser(newEmail);
		
		assertEquals(registeredUser.getEmail(), newUser.getEmail());
	}
	
	@Test
	void testAdminRegistration() {
		String newEmail = "adminguy@gmail.com";
		// New Constructor format
		
		User newAdminUser = new User("Adman","Guy",newEmail,"password","ub8",UserRole.ADMIN);
		// register the new user
		us.registerUser(newAdminUser);
		
		// Create new user, and add him as an Admin
		User registeredUser = us.getUser(newEmail);
		us.createAdminForUser(newAdminUser);
		boolean isAdmin = us.isAdmin(newEmail);
		
		assertEquals(registeredUser.getEmail(), newAdminUser.getEmail());
		assertTrue(isAdmin);
	}
	
	
	@Test
	void testEnvironmentalistRegistration() {
		String newEmail = "envoguy@gmail.com";
		// New Constructor format
		
		User newEnvoUser = new User("Envor","Guy",newEmail,"password","ub8",UserRole.USER);
		// register the new user
		us.registerUser(newEnvoUser);
		
		// Register new user
		User registeredUser = us.getUser(newEmail);
		boolean isAdmin = us.isAdmin(newEmail);
		
		assertEquals(registeredUser.getEmail(), newEnvoUser.getEmail());
		assertFalse(isAdmin);
	}
	
}
