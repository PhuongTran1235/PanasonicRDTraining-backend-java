package com.training.articles.config;

import com.training.articles.model.Articles;
import com.training.articles.service.ArticlesService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("articleSecurity")
public class ArticleSecurity {

    @Autowired
    private ArticlesService articlesService;

    public boolean checkAuthor(Long articleId) {
        String currentUserId = getCurrentUserId();
        Articles article = articlesService.getArticleById(articleId);
        return article != null && article.getAuthorId().equals(currentUserId);
    }

    public String getCurrentUserId() {
        KeycloakAuthenticationToken authentication =
                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal principal = (KeycloakPrincipal) authentication.getPrincipal();
        return principal.getKeycloakSecurityContext().getToken().getSubject();
    }

}
