package com.mbabski.logincounter.controller;

import com.mbabski.logincounter.dto.ResponseDTO;
import com.mbabski.logincounter.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

	UserService userService;

	@GetMapping("/{login}")
	ResponseDTO getUser(@PathVariable String login) {
		return userService.getResponseDto(login);
	}

}
