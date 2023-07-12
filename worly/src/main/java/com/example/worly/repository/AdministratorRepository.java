package com.example.worly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.worly.user.Administrator;
import com.example.worly.user.User;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long>{
		
	Administrator findByUser(User user);
	
}



