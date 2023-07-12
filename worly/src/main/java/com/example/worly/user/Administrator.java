package com.example.worly.user;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ADMINISTRATOR")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)

public class Administrator implements Serializable {

		private static final long serialVersionUID = 1L;

		
		//PKey is defined:
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long administratorID;
		

		//ToDo: map the relationship FK
		// map to userID and adminID for 
		@OneToOne
		@JoinColumn(name = "userID")
		private User user;
		
		//others attributes defined
		
		//Constructor for JPA 
		public Administrator() {}
		
		//constructor for the fields 
		public Administrator(User user) {
			this.user = user;
		}
		
		
	    //getters and setters defined: 
		public Long getAdminID() {
			return this.administratorID;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		
		
		
}


