package com.example.worly.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.worly.repository.AdministratorRepository;
import com.example.worly.repository.EnvironmentalistRepository;
import com.example.worly.repository.UserRepository;
import com.example.worly.token.VerificationToken;
import com.example.worly.user.Administrator;
import com.example.worly.user.Environmentalist;
import com.example.worly.user.User;
import com.example.worly.user.UserRole;

import lombok.AllArgsConstructor;

//class to find users when we are trying to login 
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{

	private final static String USER_NOT_FOUND= 
			"user with email %s not found";

	@Autowired
	private EnvironmentalistRepository envRepos;
	@Autowired
	private AdministratorRepository adminRepository;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private VerificationTokenService verificationTokenService;
	

	//this method gives the main functionality to the class
	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		
		return userRepository.findByEmail(email)
				.orElseThrow(() -> 
				new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
	}
	
	//business logic to register user 
	public String registerUser(User user) {
		//check if user exists.
		boolean userExists= userRepository.findByEmail(user.getEmail()).isPresent();
		
		//if it exists throw email is in db
		//resolve bug
		if(userExists) {throw new IllegalStateException("email is already in DB");		
		}
		
		//hash the password 
//		String encryptedPassword =bCryptPasswordEncoder.encode(user.getPassword());
		
		// then set the password as the hashed password
		user.setPassword(user.getPassword());
		
		//save user
		userRepository.save(user);
		
	//create a token and save it using its service
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(20),user);
		verificationTokenService.saveVerificationToken(verificationToken);
		
		return token;
	}
	
	//helps to turn the enable feature of user to TRUE 
    public int enableUser(String email) {
        return userRepository.enableUser(email);
    }
    public String generateToken(User user)
    {
    	String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token,LocalDateTime.now(),LocalDateTime.now().plusMinutes(20),user);
		verificationTokenService.saveVerificationToken(verificationToken);
		return token;
    }
    
    public void hashThePassword(User user) {
    	
		//hash the password 
//		String encryptedPassword =bCryptPasswordEncoder.encode(user.getPassword());
		
		// then set the password as the hashed password
		user.setPassword(user.getPassword());
		
		//save user
		userRepository.save(user);
    }
    
    public User getUser(String email) {
    	return userRepository.findByEmail(email).get();
    }
    
    public boolean isAdmin(String email) {
    	User user = getUser(email);
    	Collection auths = user.getAuthorities();
    	System.out.println(auths.toString());

    	for (Object u : auths) {
    		if (u.toString() == "ADMIN")
        		return true;
    	}
    	
    		return false;
    }
	
    public void createAdminForUser(User user) {
    	adminRepository.save(new Administrator(user));
    }
    

	public void createEnvironmentalistForUser(User user) {
		envRepos.save(new Environmentalist(user));
	}
	
	
	public void deleteUser(User user) {
		userRepository.deleteById(user.getUserID());
	}
}
