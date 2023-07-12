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
@Table(name = "QUESTION_ATTEMPT")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class Question_Attempt {

	
	private static final long serialVersionUID = 1L;


	//PKey is defined:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionAttemptID;

	//ToDo: map the relationship FK
	// questionID and answerID
	@ManyToOne
	@JoinColumn(name = "quizAttemptID")
	private Quiz_Attempt quiz_Attempt;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "questionID")
	private Question question;
	
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "answerID")
	private Answer answer;
	
	//ToDo: constructors defined 
	//Needs to be reviewed 

	// Don't delete this comment
	// Default constructor used by JPA - Not used by us but don't touch!
	public Question_Attempt() {}


		//constructor for the fields 
	public Question_Attempt(Quiz_Attempt quiz_Attempt, Question question, Answer answer) {
		this.quiz_Attempt = quiz_Attempt;
		this.question = question;
		this.answer = answer;
	}

	//getters and setters defined: 

	public Long getQuestionAttemptID() {
		return questionAttemptID;
	}
	public void setQuestionAttemptID(Long questionAttemptID) {
		this.questionAttemptID = questionAttemptID;
	}


	public Quiz_Attempt getQuiz_Attempt() {
		return quiz_Attempt;
	}


	public void setQuiz_Attempt(Quiz_Attempt quiz_Attempt) {
		this.quiz_Attempt = quiz_Attempt;
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public Answer getAnswer() {
		return answer;
	}


	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	
	
	
}
