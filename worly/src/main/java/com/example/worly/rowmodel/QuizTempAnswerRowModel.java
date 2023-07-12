package com.example.worly.rowmodel;

import java.util.List;

public class QuizTempAnswerRowModel {
	
	private String answerText;
	private int cfpValue;
	private boolean flag;
	private long answerID;
	private List<QuizTempRecomRowModel> recommendations;
	
	
	
	public QuizTempAnswerRowModel() {
		super();
	}

	public QuizTempAnswerRowModel(String answerText, int cfpValue, boolean flag, long answerID,
			List<QuizTempRecomRowModel> recommendations) {
		super();
		this.answerText = answerText;
		this.cfpValue = cfpValue;
		this.flag = flag;
		this.answerID = answerID;
		this.recommendations = recommendations;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public int getCfpValue() {
		return cfpValue;
	}

	public void setCfpValue(int cfpValue) {
		this.cfpValue = cfpValue;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<QuizTempRecomRowModel> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<QuizTempRecomRowModel> recommendations) {
		this.recommendations = recommendations;
	}

	public long getAnswerID() {
		return answerID;
	}

	public void setAnswerID(long answerID) {
		this.answerID = answerID;
	}
	
	
	

	
	
	
	
	
	
	


	
	
}
