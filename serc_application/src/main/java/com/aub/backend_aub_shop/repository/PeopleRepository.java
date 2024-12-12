package com.aub.backend_aub_shop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.PeopleModel;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleModel, UUID>{
    // Page<PeopleModel> findByName_enContaining (String nameEn, Pageable pageable);
    Boolean existsByNameEn(String nameEn);
}
