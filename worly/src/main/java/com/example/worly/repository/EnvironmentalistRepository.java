package com.example.worly.repository;

import com.example.worly.user.Environmentalist;
import com.example.worly.user.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentalistRepository extends JpaRepository<Environmentalist, Long>{
		
	Environmentalist findByUser(User user);
	
}






