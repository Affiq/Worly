package com.example.worly.model;

import javax.persistence.CascadeType;
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
@Table(name = "ANSWER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class Answer {
	
	private static final long serialVersionUID = 1L;

	//PKey is defined:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long answerID;
	
	//ToDo: map the relationship FK
	// map to questionID
	@ManyToOne
	@JoinColumn(name = "questionID")
	private Question question;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "questionAttemptID")
	private Question_Attempt questionAttempt;
	
	//other attributes defined
	private String answerText;
	private int CFPValue;
	private boolean flag;
	
	
	//constructors needs to be reviewed
	//for jpa
	public Answer() {}

	public Answer(Question question, String answerText, int CFPValue, boolean flag) {
		this.question = question;
		this.answerText= answerText;
		this.CFPValue = CFPValue;
		this.flag =flag;		
	}

	// getters and setters are defined
	
	public Long getAnswerID() {
		return answerID;
	}

	public void setAnswerID(Long answerID) {
		this.answerID = answerID;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public int getCFPValue() {
		return CFPValue;
	}

	public void setCFPValue(int cFPValue) {
		CFPValue = cFPValue;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
