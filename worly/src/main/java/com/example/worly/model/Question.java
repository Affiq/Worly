package com.example.worly.model;

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
@Table(name = "QUESTION")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class Question {

	private static final long serialVersionUID = 1L;


	//PKey is defined:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionID;

	//ToDo: map the relationship FK
	// map to quizTemplate  
	@ManyToOne
	@JoinColumn(name = "quizTemplateID")
	private QuizTemplate quizTemplate;

	//others attributes defined
	private String questionText;	
	private String category;

	//Not in erd
	//	private int questionMarks;

	//constructors defined 
	//Needs to be reviewed 

	// Don't delete this comment
	// Default constructor used by JPA - Not used by us but don't touch!
	public Question() {}


	//constructor for the fields 
	public Question(QuizTemplate quizTemplate, String questionText, String category) {
		this.quizTemplate = quizTemplate;
		this.questionText=questionText;
		this.category = category;
	}



	//getters and setters defined: 

	public Long getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
	}

	public QuizTemplate getQuiz_Template() {
		return quizTemplate;
	}
	
	public void setQuiz_Template(QuizTemplate quizTemplate) {
		this.quizTemplate = quizTemplate;
	}

	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}




	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}



//
//	public int getQuestionMarks() {
//		return questionMarks;
//	}
//	public void setQuestionMarks(int questionMarks) {
//		this.questionMarks = questionMarks;
//	}

}
