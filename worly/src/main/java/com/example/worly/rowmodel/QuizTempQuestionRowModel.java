package com.example.worly.rowmodel;

import java.util.List;

public class QuizTempQuestionRowModel {
	
	private String questionText;
	private long questionID;
	private String category;
	private List<QuizTempAnswerRowModel> answers;
	
	public QuizTempQuestionRowModel(String questionText, long questionID, String category,
			List<QuizTempAnswerRowModel> answers) {
		super();
		this.questionText = questionText;
		this.questionID = questionID;
		this.category = category;
		this.answers = answers;
	}
	
	public QuizTempQuestionRowModel() {
		// TODO Auto-generated constructor stub
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

	public List<QuizTempAnswerRowModel> getAnswers() {
		return answers;
	}
	public void setAnswers(List<QuizTempAnswerRowModel> answers) {
		this.answers = answers;
	}

	public long getQuestionID() {
		return questionID;
	}

	public void setQuestionID(long questionID) {
		this.questionID = questionID;
	}
	
}
	

