package com.example.worly.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.worly.repository.VerificationTokenRepository;
import com.example.worly.token.VerificationToken;
import com.example.worly.user.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VerificationTokenService {

	@Autowired 
	private VerificationTokenRepository verificationTokenRepo;
	
	//save token in repos.
	public void saveVerificationToken(VerificationToken token) {
		verificationTokenRepo.save(token);
	}
	
	//get token from repo
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenRepo.findByToken(token);
    }

    //save the confirmation time in repo
    public int setConfirmedAt(String token) {
        return verificationTokenRepo.updateConfirmedAt(token, LocalDateTime.now());
    }

	public void deleteVerificationTokenByUser(User user) {
		verificationTokenRepo.deleteByUser(user);
		
	}
}
