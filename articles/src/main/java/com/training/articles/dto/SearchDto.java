package com.training.articles.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class SearchDto {
    private int page = 0;
    private int size = 10;
    private String search;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
