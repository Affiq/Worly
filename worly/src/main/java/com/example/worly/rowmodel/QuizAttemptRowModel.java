package com.example.worly.rowmodel;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.example.worly.model.Question_Attempt;
import com.example.worly.model.QuizTemplate;
import com.example.worly.user.Environmentalist;

public class QuizAttemptRowModel {

	private QuizTemplate quizTemplate;
	private Date date;
	private Time time;	
	private int quizScore;
	private Environmentalist environmentalist;
	private Long quizAttemptID;
	private List<QuestionAttemptRowModel> questionAttemptList;
	
	// empty constructor
	public QuizAttemptRowModel() {
	}
	
	// full constructor
	public QuizAttemptRowModel(QuizTemplate quizTemplate, Date date, Time time, int quizScore,
			Environmentalist environmentalist, Long quizAttemptID, List<QuestionAttemptRowModel> questionAttemptList) {
		super();
		this.quizTemplate = quizTemplate;
		this.date = date;
		this.time = time;
		this.quizScore = quizScore;
		this.environmentalist = environmentalist;
		this.quizAttemptID = quizAttemptID;
		this.questionAttemptList = questionAttemptList;
	}

	// partial constructor - one without an environmentalist (user not logged in)
	public QuizAttemptRowModel(QuizTemplate quizTemplate, Date date, Time time, int quizScore, Long quizAttemptID,
			List<QuestionAttemptRowModel> questionAttemptList) {
		super();
		this.quizTemplate = quizTemplate;
		this.date = date;
		this.time = time;
		this.quizScore = quizScore;
		this.quizAttemptID = quizAttemptID;
		this.questionAttemptList = questionAttemptList;
		this.environmentalist = null;
	}

	
	public QuizTemplate getQuizTemplate() {
		return quizTemplate;
	}
	public void setQuizTemplate(QuizTemplate quizTemplate) {
		this.quizTemplate = quizTemplate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getTime() {
		return time;
	}
	public void setTime(Time time) {
		this.time = time;
	}
	public int getQuizScore() {
		return quizScore;
	}
	public void setQuizScore(int quizScore) {
		this.quizScore = quizScore;
	}
	public Environmentalist getEnvironmentalist() {
		return environmentalist;
	}
	public void setEnvironmentalist(Environmentalist environmentalist) {
		this.environmentalist = environmentalist;
	}
	public Long getQuizAttemptID() {
		return quizAttemptID;
	}
	public void setQuizAttemptID(Long quizAttemptID) {
		this.quizAttemptID = quizAttemptID;
	}
	public List<QuestionAttemptRowModel> getQuestionAttemptList() {
		return questionAttemptList;
	}

	public void setQuestionAttemptList(List<QuestionAttemptRowModel> questionAttemptList) {
		this.questionAttemptList = questionAttemptList;
	}
	
	

	
	
}
