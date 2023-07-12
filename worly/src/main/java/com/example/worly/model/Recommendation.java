package com.example.worly.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "RECOMMENDATION")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class Recommendation {
	
	private static final long serialVersionUID = 1L;


	//PKey is defined:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recommendationID;

	
	//ToDo: map the relationship FK
	// map to answerID
	@ManyToOne
	@JoinColumn(name = "answerID")
	private Answer answer;
	
	//other attributes defined
	private String recommendationText;
	
	
	
	// Don't delete this comment
	// Default constructor used by JPA - Not used by us but don't touch!
	public Recommendation() {}

	public Recommendation(Answer answer, String recommendationText) {
		this.answer = answer;
		this.recommendationText= recommendationText;
	}
	

	// getters and setters are defined - NEEDS GETTERS/SETTERS FOR QUESTION
	
	public Long getRecommendationID() {
		return recommendationID;
	}

	public void setRecommendationID(Long recommendationID) {
		this.recommendationID = recommendationID;
	}

	public String getRecommendationText() {
		return recommendationText;
	}

	public void setRecommendationText(String recommendationText) {
		this.recommendationText = recommendationText;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}


	
}
