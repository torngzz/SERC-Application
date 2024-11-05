package com.aub.backend_aub_shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.aub.backend_aub_shop.model.ArticleModel;

public interface ArticleRepository extends JpaRepository<ArticleModel, Long> {
    Page<ArticleModel> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}
