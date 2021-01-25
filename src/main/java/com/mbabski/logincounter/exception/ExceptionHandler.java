package com.mbabski.logincounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ExceptionHandler extends ResponseEntityExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
	ResponseEntity<UserNotFoundErrorDTO> handleUserNotFoundException(UserNotFoundException exception, WebRequest request) {
		final HttpStatus status = HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(new UserNotFoundErrorDTO(exception, request, status), status);
	}

}
