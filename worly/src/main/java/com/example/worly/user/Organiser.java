package com.example.worly.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ORGANISER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)

public class Organiser implements Serializable {

		private static final long serialVersionUID = 1L;

		
		//PKey is defined:
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long organiserID;
		

		//ToDo: map the relationship FK
		// map to userID and adminID for 
		@OneToOne
		@JoinColumn(name = "userID")
		private User user;
		
		@ManyToOne
		@JoinColumn(name = "administratorID")
		private Administrator administrator;
		
		//others attributes defined
		private String organisation;
		private String phoneNO;
		
		// Don't delete this comment
		// Default constructor used by JPA - Not used by us but don't touch!
		public Organiser() {}
		
		//constructor for the fields 
		public Organiser(User user, Administrator administrator, String organisation, String phoneNO) {
			this.user = user;
			this.administrator = administrator;
			this.organisation = organisation;
			this.phoneNO = phoneNO;
		}
		
	    //getters and setters defined: 
		public Long getOrganiserID() {
			return organiserID;
		}
		public void setOrganiserID(Long organiserID) {
			this.organiserID = organiserID;
		}
		
		public Administrator getAdministrator() {
			return this.administrator;
		}
		
		public void setAdministrator(Administrator administrator) {
			this.administrator = administrator;
		}
		
		public String getOrganisation() {
			return organisation;
		}
		public void setOrganisation(String organisation) {
			this.organisation = organisation;
		}
		
		public String getPhoneNO() {
			return phoneNO;
		}
		public void setPhoneNO(String phoneNO) {
			this.phoneNO = phoneNO;
		}
		
		
}


