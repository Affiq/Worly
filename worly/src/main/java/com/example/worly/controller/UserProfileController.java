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

import com.example.worly.repository.UserRepository;
import com.example.worly.request.Message;
import com.example.worly.request.NewPasswordRequest;
import com.example.worly.service.UserProfileService;
import com.example.worly.service.UserService;
import com.example.worly.user.User;
import com.example.worly.user.UserProfile;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/details") // request mapping - checks if the url contains this
@AllArgsConstructor // so that the page is accessed through 		"http://localhost:8080/api/profile"
public class UserProfileController {
	
	// @Autowired
	// private UserProfileService service;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepos;

	@GetMapping("")
	public String viewProfilePage(Model model, HttpSession session) {
		// Following line retrieves a list of user profiles
		// List<UserProfile> listUserProfile = service.listAll();
		// model.addAttribute("listUserProfile", listUserProfile);
		
		// We will be using the UserService, User and User repository as they are alike
		// And because the user already contains the details that would be in userProfile
		
		// Since we are only looking at one users profile, we need to get a specific users profile only.
		// There are ways of checking if the user is logged in, but we will do this through checking the Session attribute
		
		// First we will check if the user is signed in or not.
		// If he is not, we will send him to the business logic side of the program
		if (session.getAttribute("email") == null) {
			Message msgrq = new Message();
			msgrq.setMsg("You must log in to view profile details!");
			model.addAttribute("msg", msgrq);
			
			return "erm";
		}
		
		// First we will get the UserEmail from the session
		String targetEmail = (String) session.getAttribute("email");
		
		// We will then get the user object using userRepository with the targetEmail.
		// Normally this get function should be implemented within the service
		User targetUser = userRepos.findByEmail(targetEmail).get();
		
		// We will now add this as a model attribute, which will be referenced as User
		model.addAttribute("User", targetUser);
		
		// Extra model attributeS needed for current and new password
		// (Just to avoid confusion)
		NewPasswordRequest newPassReq = new NewPasswordRequest();
		model.addAttribute("newPassReq", newPassReq);
		
		return "profile";
	}
	
	@PostMapping("")
	public String submitProfileForm(Model model, @ModelAttribute("User") User user, HttpSession session) {
						
		// Retrieval of current user using session email (using the one in the form mightve changed emails)
		String targetEmail = (String) session.getAttribute("email");
		User targetUser = userRepos.findByEmail(targetEmail).get();
		
		// The next block of code does the password changing and details changing
		// Should occur if new password field is non empty (I suppose)
		// If passwords are wrong, no details are changed at all...
		
		
		targetUser.setForename(user.getForename());
		targetUser.setSurname(user.getSurname());
		targetUser.setEmail(user.getEmail()); // May have to reconsider this!
		targetUser.setPostcode(user.getPostcode());
		
		userRepos.save(targetUser);
		
		Message msgrq = new Message();
		msgrq.setMsg("Details changed successfully!");
		model.addAttribute("msg", msgrq);
		return "erm";
	}
	
	

}
