package com.training.articles.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder(toBuilder = true)
public record GetUsersResponseDto(
@JsonProperty("id") String id,
@JsonProperty("user_name") String userName,
@JsonProperty("email") String email
) {}