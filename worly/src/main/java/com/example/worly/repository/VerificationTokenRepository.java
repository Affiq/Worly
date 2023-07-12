package com.example.worly.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.worly.token.VerificationToken;
import com.example.worly.user.User;



@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);
	
	void deleteByToken(String token);
	
    void deleteByExpiresAtLessThan(LocalDateTime date);
	
    @Transactional
    void deleteByUser(User user);
	
	
	//query to fill the ConfirmedAt field in Verification token field.
    @Transactional
    @Modifying
    @Query("UPDATE VerificationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,  LocalDateTime confirmedAt);
}
