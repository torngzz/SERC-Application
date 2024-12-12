package com.aub.backend_aub_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.MediaModel;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Long>{

}
