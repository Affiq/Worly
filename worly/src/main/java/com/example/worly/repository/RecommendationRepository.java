package com.example.worly.repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.worly.model.Answer;
import com.example.worly.model.Recommendation;

@Transactional
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long>{
	
	Optional<Recommendation> findByRecommendationID(long recommendationID);
	
	 List<Recommendation> findAllByAnswer(Answer answer);

	 void deleteByRecommendationID(long recommendationID);
	 
	 void deleteAllByAnswer(Answer answer);
}


