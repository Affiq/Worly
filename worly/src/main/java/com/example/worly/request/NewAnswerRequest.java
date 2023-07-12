package com.example.worly.request;

public class NewAnswerRequest {

	String answerText;
	int cfpValue;
	boolean flag;
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
	
	
	
}
