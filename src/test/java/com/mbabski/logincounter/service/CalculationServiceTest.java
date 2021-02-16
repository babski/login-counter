package com.mbabski.logincounter.service;

import com.mbabski.logincounter.dto.UserDTO;
import java.time.LocalDateTime;
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
		UserDTO userDTO = new UserDTO(1, "jan", "Jan", "User","fakeUrl", LocalDateTime.now(), 0, 2);

		//when
		String calculationMessage = calculationService.calculate(userDTO);

		//then
		Assertions.assertThat(calculationMessage).isEqualTo(CalculationService.ERROR_MESSAGE);
	}

	@Test
	public void shouldCalculateCorrectResultForUserWithFollowersAndPublicRepos(){
		//given
		UserDTO userDTO = new UserDTO(1, "jan", "Jan", "User","fakeUrl", LocalDateTime.now(), 2, 2);

		//when
		String calculationMessage = calculationService.calculate(userDTO);

		//then
		Assertions.assertThat(calculationMessage).isEqualTo("12,00");
	}

}