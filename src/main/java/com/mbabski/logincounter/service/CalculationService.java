package com.mbabski.logincounter.service;

import com.mbabski.logincounter.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class CalculationService {

	public static final String ERROR_MESSAGE = "Cannot calculate for user with no followers - dividing by zero is undefined";

	private static final int ROUNDING_PRECISION = 2;

	public static final double MAGIC_FACTOR1 = 6.0;

	public static final double MAGIC_FACTOR2 = 2.0;

	String calculate(UserDTO userDTO) {
		int followers = userDTO.getFollowers();
		if (followers == 0) {
			log.warn(String.format("User with login '%s' has no followers - no calculations performed", userDTO.getLogin()));
			return ERROR_MESSAGE;
		}
		return getCalculatedResult(followers, userDTO.getPublicRepos());
	}

	private String getCalculatedResult(int followers, int publicRepos) {
		double result = MAGIC_FACTOR1 / followers * (MAGIC_FACTOR2 + publicRepos);
		return String.format("%.2f", result);
	}

}
