package com.example.worly.model;


import java.sql.Time;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.worly.user.Environmentalist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "QUIZ_ATTEMPT")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class Quiz_Attempt {
	private static final long serialVersionUID = 1L;

	
	//PKey is defined:
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quizAttemptID;
	
	//ToDo: map the relationship FK
	// map to userID and quizTemplate I 
	@ManyToOne
	@JoinColumn(name = "environmentalistID")
	private Environmentalist environmentalist;
	
	@ManyToOne
	@JoinColumn(name = "quizTemplateID")
	private QuizTemplate quizTemplate;
	
	//others attributes defined
	private Date date;
	private Time time;	
	private int quizScore;
	
	
	//constructors defined 
	//Needs to be reviewed 
	
	//for JPA 
	public Quiz_Attempt() {}
	
	
	//constructor for the fields 
	public Quiz_Attempt(Environmentalist environmentalist, QuizTemplate quizTemplate, 
						Date date,  Time time, int quizScore) {
		this.environmentalist = environmentalist;		
		this.quizTemplate = quizTemplate;
		this.date= date;
		this.time =time;
		this.quizScore =quizScore;
	}

	
    //getters and setters defined: 

	public Long getQuizAttemptID() {
		return quizAttemptID;
	}

	public void setQuizAttemptID(Long quizAttemptID) {
		this.quizAttemptID = quizAttemptID;
	}
	
	
	public Environmentalist getEnvironmentalist() {
		return environmentalist;
	}
	public void setEnvironmentalist(Environmentalist environmentalist) {
		this.environmentalist = environmentalist;
	}
	
	public QuizTemplate quizTemplate() {
		return quizTemplate;
	}
	public void setEnvironmentalist(QuizTemplate quizTemplate) {
		this.quizTemplate = quizTemplate;
	}


	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	

	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	
	

	public int getQuizScore() {
		return quizScore;
	}
	public void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}

	
}
