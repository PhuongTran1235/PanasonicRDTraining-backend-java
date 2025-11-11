package com.training.articles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDto {
    @JsonProperty("result")
    private String result;
}
