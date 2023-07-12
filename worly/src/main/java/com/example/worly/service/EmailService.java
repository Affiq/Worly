package com.example.worly.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.worly.email.EmailSender;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService  implements EmailSender{

	// we want to log when we send the email
	// this is because we don't want to send the user this exception

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Async
	public void send(String to, String email) {
		//send an email
		try {
			//mail sender  mime message
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper =
					new MimeMessageHelper(mimeMessage, "utf-8");
			//passing email and html email
			helper.setText(email, true);
			helper.setTo(to);
			helper.setSubject("Verify your email");
			helper.setFrom("Admin@worly.com");
			//send email
			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			//if we it fails to send email
			LOGGER.error("Failed sending email", e);
			throw new IllegalStateException("Failed sending email");
		}
	}

}
