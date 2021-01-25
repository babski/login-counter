package com.mbabski.logincounter.exception;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class UserNotFoundErrorDTO {

	private static final String ERROR_MESSAGE = "User with login '%s' not found";

	LocalDateTime timestamp;

	int status;

	String name;

	String message;

	String path;

	public UserNotFoundErrorDTO(UserNotFoundException exception, WebRequest webRequest, HttpStatus status) {
		this.timestamp = LocalDateTime.now();
		this.status = status.value();
		this.name = status.name();
		this.message = String.format(ERROR_MESSAGE, exception.getLogin());
		this.path = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
	}
}
