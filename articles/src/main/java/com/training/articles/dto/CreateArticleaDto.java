package com.training.articles.dto;

import lombok.Data;

@Data()
public class CreateArticleaDto {
    private String title;
    private String description;
    private Long categoryId;
}
