package com.aub.backend_aub_shop.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    // Find a transaction by the user's ID (from UserModel)
    Optional<TransactionModel> findByUser_Id(UUID userId);

    // Find transactions where the user's username contains a specific string
    Page<TransactionModel> findByUser_UsernameContaining(String username, Pageable pageable);

    // Uncomment if you need this
    // TransactionModel findTopByUser_Id_UsernameAndActionAndLogoutDateIsNullOrderByLoginDateDesc(String username, LogAction action);
}