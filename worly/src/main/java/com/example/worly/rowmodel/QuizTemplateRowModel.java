package com.example.worly.rowmodel;

public class QuizTemplateRowModel {
	public QuizTemplateRowModel(String quizName, Long quizID, String buttonText, String buttonValue) {
		super();
		this.quizName = quizName;
		this.quizTemplateID = quizID;
		this.buttonText = buttonText;
		this.buttonValue = buttonValue;
	}
	
	private String quizName;
	private Long quizTemplateID;
	private String buttonText;
	private String buttonValue;
	
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public Long getQuizTemplateID() {
		return quizTemplateID;
	}
	public void setQuizTemplateID(Long quizTemplateID) {
		this.quizTemplateID = quizTemplateID;
	}
	public String getButtonText() {
		return buttonText;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public String getButtonValue() {
		return buttonValue;
	}
	public void setButtonValue(String buttonValue) {
		this.buttonValue = buttonValue;
	}

	
	
}
