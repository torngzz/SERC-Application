package com.aub.backend_aub_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.DashboardModel;

@Repository
public interface DashboardRepository extends JpaRepository<DashboardModel, Long>{

    // @Query("SELECT count(id) FROM TBL_USER")
    // Long CountUsers();
}
