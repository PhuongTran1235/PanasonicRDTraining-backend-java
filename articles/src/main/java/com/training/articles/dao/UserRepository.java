package com.training.articles.dao;

import com.training.articles.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<Users, Long> {
}
