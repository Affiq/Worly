 package com.example.worly.model;


import com.example.worly.user.Organiser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;


import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "EVENT")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)

public class Event implements Serializable {

	private static final long serialVersionUID = 1L;


	//PKey is defined:
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventID;





	//FK MainOrganiserID
	@ManyToOne 
	@JoinColumn(name = "organiserID")
	private Organiser organiser;


	

	
	//others attributes defined
	private String eventTitle;
	private String descriptionText;
	private int minVolunteersRequired;
	private int maxVolunteers;
	private int hoursExpected;
	private int currentVolunteersNum;
	private Date eventDate;
	private Time eventTime;	
	private float geolocationLatitude;
	private float geolocationLongitude;




	//constructors defined 
	//Needs to be reviewed 

	// Don't delete this comment
	// Default constructor used by JPA - Not used by us but don't touch!
	public Event() {}

	//constructor for the fields 
	public Event(Organiser organiser, String eventTitle, String descriptionText, int minVolunteersRequired, 
			int maxVolunteers, int hoursExpected, int currentVolunteersNum, Date eventDate, Time eventTime, 
			float geolocationLatitude, float geolocationLongitude) {
		this.organiser = organiser;
		this.eventTitle = eventTitle;
		this.descriptionText = descriptionText;
		this.minVolunteersRequired = minVolunteersRequired;
		this.maxVolunteers = maxVolunteers;
		this.hoursExpected= hoursExpected;
		this.currentVolunteersNum = currentVolunteersNum;
		this.eventDate = eventDate;
		this.eventTime = eventTime;
		this.geolocationLatitude = geolocationLatitude;
		this.geolocationLongitude = geolocationLongitude;



	}




	//getters and setters defined: 
	public Organiser getOrganiser() {
		return organiser;
	}

	public void setOrganiser(Organiser organiser) {
		this.organiser = organiser;
	}

	public Long getEventID() {
		return eventID;
	}
	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}


	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}


	public String getDescriptionText() {
		return descriptionText;
	}
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public int getMinVolunteersRequired() {
		return minVolunteersRequired;
	}
	public void setMinVolunteersRequired(int minVolunteersRequired) {
		this.minVolunteersRequired = minVolunteersRequired;
	}
	
	public int getMaxVolunteers() {
		return maxVolunteers;
	}
	public void setMaxVolunteers(int maxVolunteers) {
		this.maxVolunteers = maxVolunteers;
	}
	
	public int getHoursExpected() {
		return hoursExpected;
	}
	public void setHoursExpected(int hoursExpected) {
		this.hoursExpected = hoursExpected;
	}
	
	public int getCurrentVolunteersNum() {
		return currentVolunteersNum;
	}
	public void setCurrentVolunteersNum(int currentVolunteersNum) {
		this.currentVolunteersNum = currentVolunteersNum;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	

	public Time getEventTime() {
		return eventTime;
	}
	public void setEventTime(Time eventTime) {
		this.eventTime = eventTime;
	}
	
	public float getGeolocationLatitude() {
		return geolocationLatitude;
	}
	public void setGeolocationLatitude(float geolocationLatitude) {
		this.geolocationLatitude = geolocationLatitude;
	}
	
	public float getGeolocationLongitude() {
		return geolocationLongitude;
	}
	public void setGeolocationLongitude(float geolocationLongitude) {
		this.geolocationLongitude = geolocationLongitude;
	}
}


