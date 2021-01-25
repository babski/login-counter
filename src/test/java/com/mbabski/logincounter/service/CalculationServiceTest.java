package com.mbabski.logincounter.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculationServiceTest {

	@Autowired
	private CalculationService calculationService;

	@Test
	public void shouldReturnErrorMessageForUserWithNoFollowers(){
		//given
		int followers = 0;
		int publicRepos = 2;

		//when
		String calculationMessage = calculationService.calculate(followers, publicRepos);

		//then
		Assertions.assertThat(calculationMessage).isEqualTo(CalculationService.ERROR_MESSAGE);
	}

	@Test
	public void shouldCalculateCorrectResultForUserWithFollowersAndPublicRepos(){
		//given
		int followers = 2;
		int publicRepos = 2;

		//when
		String calculationMessage = calculationService.calculate(followers, publicRepos);

		//then
		Assertions.assertThat(calculationMessage).isEqualTo("12.00");
	}

}