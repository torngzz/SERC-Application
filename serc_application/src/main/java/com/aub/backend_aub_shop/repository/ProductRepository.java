package com.aub.backend_aub_shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aub.backend_aub_shop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Product> findByProductNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

  ///  Page<Product> findByCreate_byContaining(@Param("createBy") String createBy, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE p.product_id = :id")
    void deleteById(@Param("id") Long id);
}
