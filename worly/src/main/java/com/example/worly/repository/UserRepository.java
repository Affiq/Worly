package com.example.worly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.worly.user.User;

@Repository
//@Transactional(readOnly =true)
public interface UserRepository extends CrudRepository<User,Long>{

	Optional<User> findByEmail(String email);
	
	
	//ENABLE feature of USER is set to true, once they are confirmed
	@Transactional
	@Modifying
	@Query("UPDATE User a " +
			"SET a.enabled = TRUE WHERE a.email = ?1")
	int enableUser(String email);
}
