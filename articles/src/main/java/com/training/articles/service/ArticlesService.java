package com.training.articles.service;

import com.training.articles.dao.ArticlesRepository;
import com.training.articles.dao.CategoryRepository;
import com.training.articles.dto.UpdateArticleDto;
import com.training.articles.model.Articles;
import com.training.articles.model.Category;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final CategoryRepository categoryRepository;

    public ArticlesService(ArticlesRepository articlesRepository, CategoryRepository categoryRepository) {
        this.articlesRepository = articlesRepository;
        this.categoryRepository = categoryRepository;
    }

    public Articles updateArticles(UpdateArticleDto updateArticleDto,  Long articleId) {
        Articles article = articlesRepository.findById(articleId).orElseThrow(()
                -> new RuntimeException("Article not found"));

        if(updateArticleDto.getTitle() != null) article.setTitle(updateArticleDto.getTitle());
        if(updateArticleDto.getDescription() != null) article.setDescription(updateArticleDto.getDescription());
        if(updateArticleDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateArticleDto.getCategoryId()).orElseThrow(()
                    -> new RuntimeException("Category not found"));
            article.setCategory(category);
        }

        articlesRepository.save(article);
        return article;
    }
}
