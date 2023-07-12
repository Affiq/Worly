package com.example.worly.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.worly.email.EmailSender;
import com.example.worly.repository.UserRepository;
import com.example.worly.request.ForgetPasswordRequest;
import com.example.worly.token.VerificationToken;
import com.example.worly.user.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor

public class PasswordService {

	@Autowired
	private UserService userService;

	
	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	EmailSender emailSender;
	
	@Autowired
	private UserRepository userRepos;
	

	

	public void resetToken(String request) {
		
	
		boolean isExistingEmail = false;
		boolean isAccountEnabled = false;
		String token;
		//get the user by the email in the request
		Optional<User> User = userRepos.findByEmail(request);
		
		//check if the user is present and the account is enabled
		if (User.isPresent())
		{
			isExistingEmail = true;
			isAccountEnabled = User.get().isEnabled();
		}

		// if the user is present and account is registered meaning the account is enabled 
		// DELETE THE TOKEN
		// create a new token 
		// send the token token in email
		if((isExistingEmail==true)&&(isAccountEnabled == true) ) {
			// If account is enabled
				System.out.println("deleting token");
				// Business logic - find user, delete token with user, create new token with user 
				User registeredUser = User.get();
				verificationTokenService.deleteVerificationTokenByUser(registeredUser);
				token = userService.generateToken(registeredUser);
				String link ="http://localhost:8080/api/sign/forget/confirm?token="+token;
				emailSender.send(request, buildEmail("user", link )  );

			}
		else
		{
		throw new IllegalStateException("You are not registerred yet");
		}
		
	
	}


	//takes token as param 
	//when user types the url the get request is mapped to this method
	@Transactional
	public void confirmToken(String token) {
		//check the token exists in table
		VerificationToken verificationToken = verificationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
	
		// if the expired time is before current time
		// then verification token expires
		//TODO: change the exceptions to html page with errors 
		LocalDateTime expiredAt = verificationToken.getExpiresAt();
		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}
		//other wise set the confirmed date to now
		verificationTokenService.setConfirmedAt(token);
		// enable user is basically setting the enable to true

	}
	
	//find the user by their email
	//set their password to the password in request
	public void resetPassword(ForgetPasswordRequest request) {
		Optional<User> User = userRepos.findByEmail(request.getEmail());
		User user = User.get();
		
		user.setPassword(request.getPassword());
		userService.hashThePassword(user);
		
	}
	
	//method to build email
	  private String buildEmail(String name, String link) {
	        return "        \n" +
	                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Please click on the below link to reset your password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset your password</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
	                "        \n" +
	                "</div></div>";
	    }
}
