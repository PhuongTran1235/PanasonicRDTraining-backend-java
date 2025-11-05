package com.training.articles.dao;

import com.training.articles.model.Articles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    @Query("SELECT a FROM Articles a " +
            "WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.author.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.category.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Articles> searchByTitleAuthorOrCategory(@Param("search") String search, Pageable pageable);

}
