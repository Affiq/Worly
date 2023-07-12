package com.example.worly.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.example.worly.user.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
//gonna store this in db
public class VerificationToken {

	@SequenceGenerator(
			name= "token_sequence",
			sequenceName= "token_sequence",
			allocationSize =1)
	@Id
	@GeneratedValue(
			strategy =GenerationType.SEQUENCE,
			generator ="token_sequence")
	private Long id;



	@Column(nullable = false)
	private String token;
	//keeping the track of localdatetime, expiresat and confirmed at/
	@Column(nullable = false)
	private LocalDateTime localDateTime;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

	// one user can have many tokens
	@ManyToOne
	@JoinColumn(
			nullable = false,
			name =" user_id")
	private User user;

	public VerificationToken(String token, LocalDateTime localDateTime, LocalDateTime expiredAt,User user) {
		super();
		this.token = token;
		this.localDateTime = localDateTime;
		this.expiresAt = expiredAt;
		this.user= user;
	}
	
	public VerificationToken () {
		
	}
	public LocalDateTime getConfirmedAt() {
		return confirmedAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
		this.confirmedAt = confirmedAt;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



}
