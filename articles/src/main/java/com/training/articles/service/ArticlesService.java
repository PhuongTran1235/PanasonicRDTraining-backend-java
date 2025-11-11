package com.training.articles.service;

import com.training.articles.dao.ArticlesRepository;
import com.training.articles.dao.CategoryRepository;
import com.training.articles.dto.ResponseDto;
import com.training.articles.dto.SearchDto;
import com.training.articles.dto.article.CreateArticleaDto;
import com.training.articles.dto.article.UpdateArticleDto;
import com.training.articles.model.Articles;
import com.training.articles.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final CategoryRepository categoryRepository;

    public ArticlesService(ArticlesRepository articlesRepository, CategoryRepository categoryRepository) {
        this.articlesRepository = articlesRepository;
        this.categoryRepository = categoryRepository;
    }

    public Articles createArticle(CreateArticleaDto createArticleaDto) {
        Articles article = new Articles();
        if(createArticleaDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(createArticleaDto.getCategoryId()).orElseThrow(()
                    -> new RuntimeException("Category not found"));
            article.setCategory(category);
        }
        article.setTitle(createArticleaDto.getTitle());
        article.setDescription(createArticleaDto.getDescription());
        return article;
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

    public Page<Articles> searchByTitleOrAuthorOrCategory(SearchDto searchDto){
    return articlesRepository.searchByTitleAuthorOrCategory(searchDto.getSearch(), searchDto.toPageable());
    }

    public Articles getArticleById(Long articleId) {
        return articlesRepository.findById(articleId).orElseThrow(()
                -> new RuntimeException("Article not found"));
    }

    public ResponseDto deleteArticleById(Long articleId) {
        articlesRepository.deleteById(articleId);
        var response = new  ResponseDto();
        response.setResult(String.format("Delete article %s successful", articleId));
        return response;
    }
}
