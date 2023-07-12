package com.example.worly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.worly.repository.UserProfileRepository;
import com.example.worly.user.UserProfile;

@Service
public class UserProfileService {
	@Autowired
	private UserProfileRepository repo;
	
	public List<UserProfile> listAll(){
		return repo.findAll();
	}
	
	public void save(UserProfile userProfile) {
		repo.save(userProfile);
	}
	
	public UserProfile get(Long id) {
		return repo.findById(id).get();
	}
	
}
