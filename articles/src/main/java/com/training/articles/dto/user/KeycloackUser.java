package com.training.articles.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder(toBuilder = true)
public record KeycloackUser(@JsonProperty("first_name")  String firstName,
                            @JsonProperty("last_name") String lastName,
                            String email,
                            String username,
                            String password)  {
}
