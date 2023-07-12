package com.example.worly.register;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

// check if email  valid
@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String>{

	public static Pattern emailRegEx = 
			// Puts it in the format abc123@abc123.xxxxxx
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	@Override
	public boolean test(String email) {
        Matcher emailRegExMatcher = emailRegEx.matcher(email);
		boolean emailMatch = emailRegExMatcher.find();
		return emailMatch;
	}

	
}
