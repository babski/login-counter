package com.mbabski.logincounter.service;

import com.mbabski.logincounter.dto.ResponseDTO;
import com.mbabski.logincounter.dto.UserDTO;
import com.mbabski.logincounter.exception.UserNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

	private static final String BASE_URL = "https://api.github.com/users/";

	CalculationService calculationService;

	RequestCountingService requestCountingService;

	RestTemplate restTemplate;

	public UserService(CalculationService calculationService, RequestCountingService requestCountingService) {
		this.calculationService = calculationService;
		this.requestCountingService = requestCountingService;
		this.restTemplate = new RestTemplate();
	}

	public ResponseDTO getResponseDto(String login) {
		requestCountingService.incrementRequestCount(login);
		log.info(String.format("Request for user with login '%s'", login));
		UserDTO userDTO = getUserDTO(login);
		String calculations = calculationService.calculate(userDTO);
		return new ResponseDTO(userDTO, calculations);
	}

	private UserDTO getUserDTO(String login) {
		try {
			ResponseEntity<UserDTO> response = restTemplate.getForEntity(BASE_URL + login, UserDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			log.error(String.format("User with login '%s' does not exist", login));
			throw new UserNotFoundException(login);
		}
	}
}
