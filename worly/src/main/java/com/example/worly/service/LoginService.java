package com.example.worly.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.worly.repository.UserRepository;
import com.example.worly.request.LoginRequest;
import com.example.worly.user.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {

	@Autowired
	private UserService userService;


	@Autowired
	private UserRepository userRepos;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	


	public String LoginUser(LoginRequest request) {


		String password =request.getPassword();;
		//get the user by the email in the request
		User User = userRepos.findByEmail(request.getEmail()).get();
		 
//		System.out.println(password);
		String DBPassword = User.getPassword();
//		System.out.println(DBPassword);

		//check if the user is present and the account is enabled 
		if (User.isEnabled()==true){
			//encrypt the request password 
			//password = bCryptPasswordEncoder.encode(request.getPassword());

		
			//if  the password matches
			if (password.equals( User.getPassword()))
			{
				return "success";

			}
			else {
				return "passwords do not match";
			}
		}
		else {
//			System.out.println("isEnabled:" + (User.isEnabled()==true));
			return "register please";
		}



	}
}
