package com.example.worly.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.worly.user.Environmentalist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ATTEND_EVENT")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)
public class Attend_Event {

	
	private static final long serialVersionUID = 1L;


	//PKey is defined:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attendEventID;

	//ToDo: map the relationship FK
	// questionID and answerID
	@ManyToOne
	@JoinColumn(name = "environmentalistID")
	private Environmentalist environmentalist;
	
	@ManyToOne
	@JoinColumn(name = "eventID")
	private Event event;

	
	//ToDo: constructors defined 
	//Needs to be reviewed 

	//for JPA 
	public Attend_Event() {}

		//constructor for the fields 
	public Attend_Event(Environmentalist environmentalist, Event event) {
		this.environmentalist = environmentalist;
		this.event = event;
	}






	//getters and setters defined - NEEDS EDITING: 

	public Long getAttendEventID() {
		return attendEventID;
	}
	public void setAttendEventID(Long questionAttemptID) {
		this.attendEventID = attendEventID;
	}
	
	
	
}
