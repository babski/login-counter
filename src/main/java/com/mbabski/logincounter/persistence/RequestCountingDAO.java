package com.mbabski.logincounter.persistence;

import com.mbabski.logincounter.service.RequestCountingService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class RequestCountingDAO implements RequestCountingService {

	Connection connection;

	@SneakyThrows
	RequestCountingDAO(JdbcTemplate jdbcTemplate) {
		connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
	}

	@SneakyThrows
	@Override
	public void incrementRequestCount(String login) {
		String sql = "MERGE INTO LOGIN_REQUESTS (LOGIN, REQUEST_COUNT) VALUES (?,(IFNULL((SELECT REQUEST_COUNT FROM LOGIN_REQUESTS where LOGIN = ?),0))+1)";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, login);
		statement.setString(2, login);
		statement.execute();
	}
}
