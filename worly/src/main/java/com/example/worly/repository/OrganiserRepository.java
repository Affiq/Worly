package com.example.worly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.worly.user.Organiser;
import com.example.worly.user.User;

@Repository
public interface OrganiserRepository extends JpaRepository<Organiser, Long>{
	Organiser findByUser(User user);

}


