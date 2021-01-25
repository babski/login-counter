package com.mbabski.logincounter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

	long id;

	String login;

	String name;

	String type;

	@JsonProperty("avatar_url")
	String avatarUrl;

	@JsonProperty("created_at")
	LocalDateTime createdAt;

	int followers;

	@JsonProperty("public_repos")
	int publicRepos;
}
