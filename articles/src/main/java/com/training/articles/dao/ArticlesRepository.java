package com.training.articles.dao;

import com.training.articles.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
}
