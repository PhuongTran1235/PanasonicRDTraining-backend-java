package com.training.articles.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "articles")
public class Articles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
