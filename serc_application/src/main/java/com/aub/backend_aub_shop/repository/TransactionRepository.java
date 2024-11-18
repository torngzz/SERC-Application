package com.aub.backend_aub_shop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long>{
    Optional<TransactionModel> findByUsername(String usernmaeString);

    Page<TransactionModel> findByUsernameContaining(String username, Pageable pageable);

    // Find the latest login transaction where logoutDate is null
    TransactionModel findTopByUsernameAndActionAndLogoutDateIsNullOrderByLoginDateDesc(String username, String action);
}
