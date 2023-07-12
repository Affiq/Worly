package com.example.worly.rowmodel;

public class QuizTempRecomRowModel {
	
	private String recommendationText;
	private long recommendationID;
	
	
	
	public QuizTempRecomRowModel() {
		super();
	}


	public QuizTempRecomRowModel(String recommendationText, long recommendationID) {
		super();
		this.recommendationText = recommendationText;
		this.recommendationID = recommendationID;
	}
	
	
	public String getRecommendationText() {
		return recommendationText;
	}
	public void setRecommendationText(String recommendationText) {
		this.recommendationText = recommendationText;
	}


	public long getRecommendationID() {
		return recommendationID;
	}


	public void setRecommendationID(long recommendationID) {
		this.recommendationID = recommendationID;
	}

	
	
	


	
	
}
