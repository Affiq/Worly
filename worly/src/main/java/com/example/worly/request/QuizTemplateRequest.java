package com.example.worly.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//lombok getters are not working
//class to get these fields from user
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class QuizTemplateRequest {
  private String quizName;
  private long quizTemplateID;
  private Boolean isEnabled;

  public void setQuizName(String quizName) {
	  this.quizName = quizName;
  }
  public String getQuizName() {
	  return quizName;
  }

  public void setQuizTemplateID(long quizTemplateID) {
	  this.quizTemplateID = quizTemplateID;
  }
  public long getQuizTemplateID() {
	  return quizTemplateID;
  }

  public void setEnabled(Boolean isEnabled) {
	  this.isEnabled = isEnabled;
  }
  public Boolean isEnabled() {
	  return isEnabled;
  }
}
