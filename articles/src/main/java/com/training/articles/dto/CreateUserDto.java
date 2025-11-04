package com.training.articles.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserDto {
    @NonNull
    private String name;

    @NonNull
    private String email;
}

