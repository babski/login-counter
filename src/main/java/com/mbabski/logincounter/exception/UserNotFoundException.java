package com.mbabski.logincounter.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserNotFoundException extends RuntimeException {

	String login;

	public UserNotFoundException(String login) {
		super(login);
		this.login = login;
	}
}
