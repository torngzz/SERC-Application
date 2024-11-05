package com.aub.backend_aub_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.model.ArticleModel;
import com.aub.backend_aub_shop.repository.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public Page<ArticleModel> findAll(int pageNumber, int pageSize) {
        return articleRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public ArticleModel save(ArticleModel article) {
        return articleRepository.save(article);
    }

    public ArticleModel findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }
}
