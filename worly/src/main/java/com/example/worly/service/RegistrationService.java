package com.example.worly.service;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.worly.email.EmailSender;
import com.example.worly.register.EmailValidator;
import com.example.worly.register.RegistrationRequest;
import com.example.worly.repository.EnvironmentalistRepository;
import com.example.worly.repository.UserRepository;
import com.example.worly.repository.VerificationTokenRepository;
import com.example.worly.token.VerificationToken;
import com.example.worly.user.Environmentalist;
import com.example.worly.user.User;
import com.example.worly.user.UserRole;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class RegistrationService {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailValidator emailValidator;
	
	@Autowired
	private VerificationTokenService verificationTokenService;
	
	@Autowired 
	private VerificationTokenRepository verificationTokenRepos;

	@Autowired
	private  EmailSender emailSender;
	
	@Autowired
	private UserRepository userRepos;
	
	@Autowired 
	EnvironmentalistRepository envRepos;

	
	//takes the RegistrationRequest as param and call
	//when user types the url the post request is mapped to this method
	public void register(RegistrationRequest request) throws Exception {
		
		//Check email is valid regex, existing in db and if it is enabled
		boolean isValidEmail = emailValidator.test(request.getEmail());
		boolean isExistingEmail = false;
		boolean isAccountEnabled = false;
		
		testEmailSender();
		
		Optional<User> emailExists = userRepos.findByEmail(request.getEmail());
		if (emailExists.isPresent())
		{
			isExistingEmail = true;
			isAccountEnabled = emailExists.get().isEnabled();
		}

		// EmailValidator check
		if(!isValidEmail) { throw new IllegalStateException("email not valid");}
		// ExistingEmail check
		if(isExistingEmail) {
			if (isAccountEnabled == true)
			throw new IllegalStateException("email is already registered!");
			// else
			// BUSINESS LOGIC FOR RESENDING EMAIL VERIFICATION - WITH NEW TOKEN? 
			// JUST DONT ADD ACCOUNT IN DATABASE AGAIN
			}
		
		//after checking if the email is valid
		// the return statement sends the data of TOKEN to browser as a response
		User newUser = new User(request.getFirstName(), request.getLastName(), request.getEmail(),
						request.getPassword(), request.getPostcode(), UserRole.USER);
		String token= userService.registerUser(newUser);
		userService.createEnvironmentalistForUser(newUser);
				
		try {
		String link ="http://localhost:8080/api/sign/up/confirm?token="+token;
		emailSender.send(request.getEmail(), buildEmail(request.getFirstName(), link )  );
		} catch (Exception e) {
			throw new Exception("Problem with email sender");
		}
		

	}


	//takes token as param 
	//when user types the url the get request is mapped to this method
	@Transactional
	public void confirmToken(String token) {
		//check the token exists in table
		VerificationToken verificationToken = verificationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
	
		// if token exists and is confirmed column is filled its already confirmed
		if (verificationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("email already confirmed");
		}
		// if the expired time is before current time
		// then verification token expires
		//TODO: change the exceptions to html page with errors 
		LocalDateTime expiredAt = verificationToken.getExpiresAt();
		if (expiredAt.isBefore(LocalDateTime.now())) {
			
			userService.deleteUser(verificationToken.getUser());
			verificationTokenRepos.deleteByToken(token);
			throw new IllegalStateException("token expired");
		}
		//other wise set the confirmed date to now
		verificationTokenService.setConfirmedAt(token);
		// enable user is basically setting the enable to true
		userService.enableUser(
				verificationToken.getUser().getEmail());
		
		// UNCOMMENT THE FOLLOWING LINE TO PURGE TOKEN AFTER ACCOUNT IS ENABLED
		// verificationTokenRepos.deleteByToken(token);
		
	
	}
	
	
	
	//method to build email
	  private String buildEmail(String name, String link) {
	        return  "        \n" +
	                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
	                "        \n" +
	               
	                "</div></div>";
	    }
	  
	  private void testEmailSender() throws Exception {
		  try {
				emailSender.send("randomemailtesting123@gmail.com", buildEmail("randomFirstname", "deadLink" )  );
				} catch (Exception e) {
					throw new Exception("Problem with email sender");
				}
	  }
}