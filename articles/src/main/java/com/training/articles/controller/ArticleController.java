package com.training.articles.controller;

import com.training.articles.Common;
import com.training.articles.dto.SearchDto;
import com.training.articles.dto.article.CreateArticleaDto;
import com.training.articles.dto.article.UpdateArticleDto;
import com.training.articles.model.Articles;
import com.training.articles.service.ArticlesService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController("articleController")
public class ArticleController {
    private static final String SERVICE_PATH = Common.ROOT_PATH + "/articles";
    private final ArticlesService articlesService;

    public ArticleController(ArticlesService articlesService) {
        this.articlesService = articlesService;
    }

    @GetMapping(SERVICE_PATH)
    public Page<Articles> searchByTitleOrAuthorOrCategory(@ModelAttribute SearchDto searchDto) {
        return articlesService.searchByTitleOrAuthorOrCategory(searchDto);
    }

    @PostMapping( SERVICE_PATH + "/create")
    public Articles createArticle(@RequestBody CreateArticleaDto createArticleaDto){
        return articlesService.createArticle(createArticleaDto);
    }

    @PatchMapping(SERVICE_PATH + "/update/{id}")
    public Articles updateArticle(@RequestBody UpdateArticleDto updateArticleDto, @PathVariable Long id){
        return articlesService.updateArticles(updateArticleDto, id);
    }
}
