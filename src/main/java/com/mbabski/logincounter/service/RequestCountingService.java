package com.mbabski.logincounter.service;

public interface RequestCountingService {

	void incrementRequestCount(String login);

}
