package com.example.worly.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.worly.user.UserRole;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Table(name = "USER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, 
allowGetters = true)

public class User implements Serializable, UserDetails {

		private static final long serialVersionUID = 1L;

		// Primary Key
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long userID;
		
		// Foreign Key Mapping
		@OneToOne(cascade= CascadeType.ALL, mappedBy="user")
		private Environmentalist environmentalist;
		@OneToOne(cascade= CascadeType.ALL, mappedBy="user")
		private Administrator administrator;
		@OneToOne(cascade= CascadeType.ALL, mappedBy="user")
		private Organiser organiser;
		
	    // Fields
		private String forename;
		private String surname;
		private String email;
		private String password;
		private String postcode;

		private String accountType;
		private UserRole userRole;	
		private Boolean locked =false;
		private Boolean enabled =false;
		
		// Default constructor used by JPA
	    protected User() {}
		
	    // Old constructor so that dbrunner doesn't throw compilation error
	    public User(String email, String password, String accountType, String forename, String surname)
	    {
	    	this.email = email;
	    	this.password = password;
	    	this.accountType = accountType;
	    	this.forename = forename;
	    	this.surname = surname;
	    }
	    
	    //ToDo: Define NEW Constructor [In line with registration]
	    public User(String firstName, String lastName, String email, String password, String postcode, UserRole userRole) {
	    	this.email = email;
	    	this.password = password;
	    	this.accountType = accountType;
	    	this.forename = firstName;
	    	this.surname = lastName;
	    	this.postcode = postcode;
	    	this.userRole = userRole;
	    }
	    
	    
	    
	 // ToDo: Define all the Getters and Setters
	    
	    public Environmentalist getEnvironmentalist() {
			return environmentalist;
		}
		public void setEnvironmentalist(Environmentalist environmentalist) {
			this.environmentalist = environmentalist;
		}

		public Administrator getAdministrator() {
			return administrator;
		}
		public void setAdministrator(Administrator administrator) {
			this.administrator = administrator;
		}

		public Organiser getOrganiser() {
			return organiser;
		}
		public void setOrganiser(Organiser organiser) {
			this.organiser = organiser;
		}
		
		public Long getUserID() {
			return userID;
		}
		public void setUserID(Long userID) {
			this.userID = userID;
		}
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getAccountType() {
			return accountType;
		}
		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}
		
		public String getSurname() {
			return surname;
		}
		public void setSurname(String surname) {
			this.surname = surname;
		}
		
		public String getForename() {
			return forename;
		}
		public void setForename(String forename) {
			this.forename = forename;
		}

		
		
		public String getPostcode() {
			return postcode;
		}

		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities(){
			SimpleGrantedAuthority authority= 
					new SimpleGrantedAuthority(userRole.name());
			return Collections.singletonList(authority);
		}

		@Override
		public String getUsername() {
			return email;
		}

		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return !locked;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return enabled;
		}

		
		
}

