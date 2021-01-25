package com.mbabski.logincounter.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
class CalculationService {

	public static final String ERROR_MESSAGE = "Cannot calculate for user with no followers - dividing by zero is undefined";

	private static final int ROUNDING_PRECISION = 2;

	String calculate(int followers, int publicRepos) {
		return followers == 0 ? ERROR_MESSAGE : getCalculatedResult(followers, publicRepos);
	}

	private String getCalculatedResult(int followers, int publicRepos) {
		BigDecimal result = BigDecimal.valueOf(6.0 / followers * (2.0 + publicRepos));
		BigDecimal roundedResult = result.setScale(ROUNDING_PRECISION, RoundingMode.HALF_UP);
		return roundedResult.toString();
	}

}
