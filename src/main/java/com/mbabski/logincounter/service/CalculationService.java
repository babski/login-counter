package com.mbabski.logincounter.service;

import com.mbabski.logincounter.dto.UserDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class CalculationService {

	public static final String ERROR_MESSAGE = "Cannot calculate for user with no followers - dividing by zero is undefined";

	private static final int ROUNDING_PRECISION = 2;

	String calculate(UserDTO userDTO) {
		int followers = userDTO.getFollowers();
		if (followers == 0) {
			log.warn(String.format("User with login '%s' has no followers - no calculations performed", userDTO.getLogin()));
			return ERROR_MESSAGE;
		}
		return getCalculatedResult(followers, userDTO.getPublicRepos());
	}

	private String getCalculatedResult(int followers, int publicRepos) {
		BigDecimal result = BigDecimal.valueOf(6.0 / followers * (2.0 + publicRepos));
		BigDecimal roundedResult = result.setScale(ROUNDING_PRECISION, RoundingMode.HALF_UP);
		return roundedResult.toString();
	}

}
