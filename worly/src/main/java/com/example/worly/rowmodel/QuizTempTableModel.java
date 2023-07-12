package com.example.worly.rowmodel;

import java.util.List;

public class QuizTempTableModel {

	private String quizName;
	private List<QuizTempQuestionRowModel> questions;
	
	public QuizTempTableModel(String quizName, List<QuizTempQuestionRowModel> questions) {
		super();
		this.quizName = quizName;
		this.questions = questions;
	}
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public List<QuizTempQuestionRowModel> getQuestions() {
		return questions;
	}
	public void setQuestions(List<QuizTempQuestionRowModel> questions) {
		this.questions = questions;
	}
	
	
	
}
