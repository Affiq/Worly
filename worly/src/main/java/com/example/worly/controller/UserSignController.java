package com.example.worly.controller;


import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.worly.register.RegistrationRequest;
import com.example.worly.repository.UserRepository;
import com.example.worly.request.ForgetPasswordRequest;
import com.example.worly.request.LoginRequest;
import com.example.worly.request.Message;
import com.example.worly.service.LoginService;
import com.example.worly.service.PasswordService;
import com.example.worly.service.RegistrationService;
import com.example.worly.service.UserService;
import com.example.worly.user.User;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping(path= "api/sign")
@AllArgsConstructor
public class UserSignController {
	
	@Autowired
	UserRepository userRepos;


	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private LoginService loginService;

	@Autowired
	PasswordService passwordService;

	@Autowired
	private UserService userService;


	//registration
	@GetMapping("")
	public String sign(Model model, HttpSession session) {
		RegistrationRequest loginRequest = new RegistrationRequest();
		model.addAttribute("loginRequest", loginRequest);

		RegistrationRequest request = new RegistrationRequest();
		model.addAttribute("request", request);

		RegistrationRequest passwordRequest = new RegistrationRequest();
		model.addAttribute("passwordRequest", passwordRequest);

		return "index";
	}

	// for Registration
	@PostMapping("up")
	public String submitForm(@ModelAttribute("request") RegistrationRequest request, Model model) {
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		try {
			registrationService.register(request);
		}
		catch (Exception exception)
		{
			if (exception.toString().contains("email not valid"))
				msgrq.setMsg("Please enter valid email!");
			if (exception.toString().contains("email is already registered!"))
				msgrq.setMsg("Email is already registered!. Please use another email");
			if (exception.toString().contains("Problem with email sender"))
				msgrq.setMsg("Problem with email sender");

			//				System.out.println("Unhandled exception: " + exception.toString());
			//				return ("unhandled error: " + exception.toString()); // Just need to change this really
		}
		String msg= msgrq.getMsg();
		if (msg== null) {
			return "confirmation";
		}
		else {
		return "erm";
		}
	}


	@GetMapping("up/confirm")
	// @ResponseBody
	public String confirmUser(@RequestParam("token") String token, Model model) {
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		try {
			registrationService.confirmToken(token);
		}
		catch (Exception exception)
		{
			if (exception.toString().contains("token not found"))
				msgrq.setMsg("Invalid token entered");
			if (exception.toString().contains("email already confirmed"))
				msgrq.setMsg( "Email has already been confirmed!") ;
			if (exception.toString().contains("token expired"))
				msgrq.setMsg( "Link used has expired - request a new link!");

		}

		String msg= msgrq.getMsg();
		if (msg ==null) {
			msgrq.setMsg("Thank you for your account has been verified! Have fun saving the world with Worly!");
		}
			return "erm";
	}


	//login
	@PostMapping("in")
	public String submitForm(Model model, @ModelAttribute("request") LoginRequest loginRequest, HttpSession session) {
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		try {
			String status =  loginService.LoginUser(loginRequest);
			//		System.out.println(status);
			if(status.equals("success")){
				String targetEmail = loginRequest.getEmail();
				session.setAttribute("loggedIn", true);
				session.setAttribute("email", targetEmail);
	
				// Sets the admin session attribute
				if (userService.isAdmin(targetEmail)) {
					session.setAttribute("admin", "true");
				}
				else {
					session.setAttribute("admin", "false");
				}
				return "LoggedInHome";
			}
			else {
				status="mismatch";
				msgrq.setMsg( "Invalid password. Please try again.");
				return "erm" ;
			}
		} catch (Exception e) {
			msgrq.setMsg( "The email you have entered is not part of the system. Please sign up.");
			return "erm";
		}
	}



	//forget password
	@PostMapping("forget")
	public String submitForgetForm(@ModelAttribute("passwordRequest") RegistrationRequest request, Model model) {
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		try {
			passwordService.resetToken(request.getEmail());
		}
		catch (Exception exception)
		{
			if (exception.toString().contains("You are not registerred yet"))
				msgrq.setMsg ("you need to register");
		}

		String msg= msgrq.getMsg();
		if (msg.length()==0) {
			msgrq.setMsg( "Thank you! You should recieve an email to reset your account. Have fun saving the world with worly!");
		}
		
			return "erm";
	}

	@GetMapping(path = "forget/confirm")
	public String confirm(@RequestParam("token") String token,Model model) {
		
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		try {

			ForgetPasswordRequest request = new  ForgetPasswordRequest();
			model.addAttribute("request", request);
			passwordService.confirmToken(token);
		}
		catch (Exception exception)
		{
			if (exception.toString().contains("token not found"))
				msgrq.setMsg("Invalid token entered");
			if (exception.toString().contains("token expired"))
				msgrq.setMsg("Link used has expired - request a new link!");

			msgrq.setMsg("unhandled error: " + exception.toString());
		}
		
		
		String msg= msgrq.getMsg();
		if (msg.length()==0) {
			return "changepassword";
		}
		else {
			return "erm";
		}
	}


	@PostMapping("forget/confirmed")
	public String changePassword(@ModelAttribute("request") ForgetPasswordRequest request,Model model) {
		
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		
		passwordService.resetPassword(request);;

		msgrq.setMsg("Your password is changed! Have fun saving the world with Worly!");
		return "erm";
	}





	//when user presses back button on browser after trying to sign in redirected to this
	@GetMapping("in")
	public String signIn(Model model, HttpSession session, Principal principal) {
		RegistrationRequest loginRequest = new RegistrationRequest();
		model.addAttribute("loginRequest", loginRequest);

		RegistrationRequest request = new RegistrationRequest();
		model.addAttribute("request", request);

		RegistrationRequest passwordRequest = new RegistrationRequest();
		model.addAttribute("passwordRequest", passwordRequest);

		if (principal == null)
			return "index";
		else
			return "redirect:/api/quiztemplates"; // or dashboard
	}

	// When user tries to log out
	// NOTE: Use POST, Reason: https://stackoverflow.com/questions/3521290/logout-get-or-post
	// Improvement: Add CSRF
	@PostMapping("out")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("out")
	public String logout(Model model, Principal principal){
		if (principal == null)
			return "redirect:/"; // Not logged in

		String user = principal.getName();
		model.addAttribute("user", user);
		return "logout";
	}

	//when user presses back button on browser after trying to sign up redirected to this
	@GetMapping("up")
	public String signUp(Model model, HttpSession session, Principal principal) {
		RegistrationRequest loginRequest = new RegistrationRequest();
		model.addAttribute("loginRequest", loginRequest);

		RegistrationRequest request = new RegistrationRequest();
		model.addAttribute("request", request);

		RegistrationRequest passwordRequest = new RegistrationRequest();
		model.addAttribute("passwordRequest", passwordRequest);

		if (principal == null)
			return "index";
		else
			return "redirect:/api/quiztemplates"; // or dashboard
	}

	//when user presses back button on browser after trying to forget password redirected to this

	@GetMapping("forget")
	public String signForget(Model model, HttpSession session, Principal principal) {
		RegistrationRequest loginRequest = new RegistrationRequest();
		model.addAttribute("loginRequest", loginRequest);

		RegistrationRequest request = new RegistrationRequest();
		model.addAttribute("request", request);

		RegistrationRequest passwordRequest = new RegistrationRequest();
		model.addAttribute("passwordRequest", passwordRequest);

		if (principal == null)
			return "index";
		else
			return "redirect:/api/quiztemplates"; // or dashboard
	}
	
	@PostMapping("/home")
	public String homePage(Model model, @ModelAttribute("User") User user, HttpSession session) {
		Message msgrq = new Message();
		model.addAttribute("msg", msgrq);
		
		
		if (session.getAttribute("email") == null) {
			msgrq.setMsg("not logged in");
			
			return "erm";
		}
		
		// First we will get the UserEmail from the session
		String targetEmail = (String) session.getAttribute("email");
		User targetUser = userRepos.findByEmail(targetEmail).get();
	
	
				return "LoggedInHome";
	}
}
