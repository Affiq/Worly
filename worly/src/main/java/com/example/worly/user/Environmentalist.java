package com.example.worly.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ENVIRONMENTALIST")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)

public class Environmentalist implements Serializable {

		private static final long serialVersionUID = 1L;

		
		//ToDo: Define keys:
		// 		primaryKey EnvironmentalistID, foreignKey userID
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long environmentalistID;
		
		@OneToOne
		@JoinColumn(name = "userID")
		private User user;
		
		
	    //ToDo: Define other fields: 
		// 		avgCFPScore, totalCFPScore, quizzesTaken, eventsAttended, hoursVolunteered
		private int avgCFPScore;
		
		private int totalCFPScore;
		
		private int quizzesTaken;
		
		private int eventsAttended;
		
		private int hoursVolunteered;
		
		

		// Default constructor used by JPA - Not used by us but don't touch!
	    protected Environmentalist() {}
		
	    //ToDo: Define Constructor(s)
	    public Environmentalist(User user, int avgCFPScore, int totalCFPScore, int quizzesTaken,
	    		int eventsAttended, int hoursVolunteered) {
	    	this.user = user;
	    	this.avgCFPScore = avgCFPScore;
	    	this.totalCFPScore = totalCFPScore;
	    	this.quizzesTaken = quizzesTaken;
	    	this.eventsAttended = eventsAttended;
	    	this.hoursVolunteered = hoursVolunteered;
	    }
	    
	    // Constructor for new account
	    public Environmentalist(User user)
	    {
	    	this.user = user;
	    	this.avgCFPScore = 0;
	    	this.totalCFPScore = 0;
	    	this.quizzesTaken = 0;
	    	this.eventsAttended = 0;
	    	this.hoursVolunteered = 0;
	    }
	    
	    
	 // ToDo: Define all the Getters and Setters 
		
	    public Long getEnvironmentalistID() {
	    	return environmentalistID;
	    }
	    
	    public void setEnvironmentalistID(long environmentalistID) {
	    	this.environmentalistID = environmentalistID;
	    }
	    
	    public User getUser() {
	    	return user;
	    }
	    
	    public void setUser(User user) {
	    	this.user = user;
	    }
	    
	    public int getAvgCFPScore() {
	    	return avgCFPScore;
	    }
	    
	    public void setAvgCFPScore(int avgCFPScore) {
	    	this.avgCFPScore = avgCFPScore;
	    }
	    
	    public int getTotalCFPScore() {
	    	return totalCFPScore;
	    }
	    
	    public void setTotalCFPScore(int totalCFPScore) {
	    	this.totalCFPScore = totalCFPScore;
	    }
	    
	    public int getQuizzesTaken() {
	    	return quizzesTaken;
	    }
	    
	    public void setQuizzesTaken(int QuizzesTaken) {
	    	this.quizzesTaken = QuizzesTaken;
	    }
	    
	    public int getEventsAttended() {
	    	return eventsAttended;
	    }
	    
	    public void set(int eventsAttended) {
	    	this.eventsAttended = eventsAttended;
	    }
	    
	    public int getHoursVolunteered() {
	    	return hoursVolunteered;
	    }
	    
	    public void setHoursVolunteered(int hoursVolunteered) {
	    	this.hoursVolunteered = hoursVolunteered;
	    }
		
}

