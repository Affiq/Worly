package com.example.worly.model;


import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "QUIZ_TEMPLATE")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class QuizTemplate {
	private static final long serialVersionUID = 1L;

	
	//PKey is defined:
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quizTemplateID;
	
	//others attributes defined
	private String quizName;
	private boolean released;
	
	// Default constructor used by JPA
	public QuizTemplate() {}
	
	public QuizTemplate(String quizName) 
	{
		this.quizName = quizName;
		this.released = false;
	}
	
    //getters and setters defined - NEED TO ADD GETTERS AND SETTERS: 

	public Long getQuizTemplateID() {
		return quizTemplateID;
	}
	public void setQuizTemplateID(Long quizTemplateID) {
		this.quizTemplateID = quizTemplateID;
	}
	
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	
	public boolean isReleased() {
		return released;
	}
	public void setReleased(boolean released) {
		this.released = released;
	}
}
