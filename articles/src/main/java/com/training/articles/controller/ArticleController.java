package com.training.articles.controller;

import com.training.articles.Common;
import com.training.articles.config.ArticleSecurity;
import com.training.articles.dto.ResponseDto;
import com.training.articles.dto.SearchDto;
import com.training.articles.dto.article.CreateArticleaDto;
import com.training.articles.dto.article.UpdateArticleDto;
import com.training.articles.model.Articles;
import com.training.articles.service.ArticlesService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("articleController")
public class ArticleController {
    private static final String SERVICE_PATH = Common.ROOT_PATH + "/articles";
    private final ArticlesService articlesService;
    private final ArticleSecurity  articleSecurity;

    public ArticleController(ArticlesService articlesService,  ArticleSecurity articleSecurity) {
        this.articlesService = articlesService;
        this.articleSecurity = articleSecurity;
    }

    @GetMapping(SERVICE_PATH)
    public Page<Articles> searchByTitleOrAuthorOrCategory(@ModelAttribute SearchDto searchDto) {
        return articlesService.searchByTitleOrAuthorOrCategory(searchDto);
    }

    @PostMapping( SERVICE_PATH + "/create")
    @PreAuthorize("hasRole('user')")
    public Articles createArticle(@RequestBody CreateArticleaDto createArticleaDto){
        return articlesService.createArticle(createArticleaDto);
    }

    @PatchMapping(SERVICE_PATH + "/update/{id}")
    @PreAuthorize("hasRole('user') and @articleSecurity.checkAuthor(#id)")
    public Articles updateArticle(@RequestBody UpdateArticleDto updateArticleDto, @PathVariable Long id) {
        if (!articleSecurity.checkAuthor(id)) {
            throw new AccessDeniedException("You are not the author of this article.");
        }
        return articlesService.updateArticles(updateArticleDto, id);
    }

    @DeleteMapping(SERVICE_PATH + "/delete/{id}")
    @PreAuthorize("hasRole('admin') or (hasRole('user') and @articleSecurity.checkAuthor(#id))")
    public ResponseDto deleteArticle(@PathVariable Long id) {
        return articlesService.deleteArticleById(id);
    }

}
