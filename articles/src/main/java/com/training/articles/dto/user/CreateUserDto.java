package com.training.articles.dto.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserDto {
    @NonNull
    private String name;

    @NonNull
    private String email;
}

