package com.training.articles.model;

import com.training.articles.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Articles> articles;

    @Enumerated(EnumType.STRING)
    private Role role;

}
