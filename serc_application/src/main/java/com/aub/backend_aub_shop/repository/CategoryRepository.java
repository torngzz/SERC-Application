package com.aub.backend_aub_shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.aub.backend_aub_shop.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Long>{
   
    public Page<Category> findByCategoryNameContainingIgnoreCase(@Param("cate_name")String cate_name, Pageable pageable);  
}
