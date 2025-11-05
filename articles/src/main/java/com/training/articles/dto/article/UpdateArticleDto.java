package com.training.articles.dto.article;

import lombok.Data;

@Data
public class UpdateArticleDto {
    private String title;
    private String description;
    private Long categoryId;
}
