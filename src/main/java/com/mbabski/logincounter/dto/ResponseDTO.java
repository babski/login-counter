package com.mbabski.logincounter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResponseDTO {

	long id;

	String login;

	String name;

	String type;

	@JsonProperty("avatar_url")
	String avatarUrl;

	@JsonProperty("created_at")
	LocalDateTime createdAt;

	String calculations;

	public ResponseDTO(UserDTO userDTO, String calculations) {
		this.id = userDTO.getId();
		this.login = userDTO.getLogin();
		this.name = userDTO.getName();
		this.type = userDTO.getType();
		this.avatarUrl = userDTO.getAvatarUrl();
		this.createdAt = userDTO.getCreatedAt();
		this.calculations = calculations;

	}
}
