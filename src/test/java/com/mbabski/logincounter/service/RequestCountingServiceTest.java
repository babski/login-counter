package com.mbabski.logincounter.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestCountingServiceTest {

	@Autowired
	private RequestCountingService requestCountingService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void shouldReturn50AsyncRequestCounts() throws InterruptedException {
		//given
		String login = "njk23n4jk2nkda";
		ExecutorService executor = Executors.newFixedThreadPool(20);

		//when
		for (int i = 0; i < 50; i++) {
			executor.execute(() -> requestCountingService.incrementRequestCount(login));
		}
		Thread.sleep(100);

		//then
		Assertions.assertThat(countRequests(login)).isEqualTo(50);
	}

	@SneakyThrows
	private int countRequests(String login) {
		Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
		String query = "SELECT LOGIN, REQUEST_COUNT FROM LOGIN_REQUESTS WHERE LOGIN = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, login);
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next() ? resultSet.getInt(2) : 0;
	}
}