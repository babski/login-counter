package com.mbabski.logincounter.service;

import com.mbabski.logincounter.dto.ResponseDTO;
import com.mbabski.logincounter.dto.UserDTO;
import com.mbabski.logincounter.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserService {

	private static final String BASE_URL = "https://api.github.com/users/";

	CalculationService calculationService;

	RequestCountingService requestCountingService;

	public ResponseDTO getResponseDto(String login) {
		requestCountingService.incrementRequestCount(login);
		UserDTO userDTO = getUserDTO(login);
		String calculations = calculationService.calculate(userDTO.getFollowers(), userDTO.getPublicRepos());
		return new ResponseDTO(userDTO, calculations);
	}

	private UserDTO getUserDTO(String login) {
		try {
			ResponseEntity<UserDTO> response = new RestTemplate().getForEntity(BASE_URL + login, UserDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new UserNotFoundException(login);
		}
	}
}
