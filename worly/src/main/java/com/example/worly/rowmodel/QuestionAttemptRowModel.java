package com.example.worly.rowmodel;

public class QuestionAttemptRowModel {

	private long answerID;
	private long questionID;
	private String answerText;
	
	public QuestionAttemptRowModel(long answerID, long questionID) {
		this.answerID = answerID;
		this.questionID = questionID;
	}
	
	public QuestionAttemptRowModel(long answerID, long questionID, String answerText) {
		this.answerID = answerID;
		this.questionID = questionID;
		this.answerText = answerText;
	}

	public QuestionAttemptRowModel() {
	}

	public long getAnswerID() {
		return answerID;
	}
	public void setAnswerID(long answerID) {
		this.answerID = answerID;
	}
	public long getQuestionID() {
		return questionID;
	}
	public void setQuestionID(long questionID) {
		this.questionID = questionID;
	}
	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	} 
	
	
	
}
