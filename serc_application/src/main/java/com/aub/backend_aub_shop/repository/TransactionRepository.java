package com.aub.backend_aub_shop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.TransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {

    // Correcting the method to access UserModel's 'username' field via a join
    Optional<TransactionModel> findByUsername_Username(String username);

    // Find transactions where the username's username contains a specific string
    Page<TransactionModel> findByUsername_UsernameContaining(String username, Pageable pageable);

    // Find the latest login transaction where logoutDate is null (uncomment if needed)
    // TransactionModel findTopByUsernameAndActionAndLogoutDateIsNullOrderByLoginDateDesc(String username, String action);
}
