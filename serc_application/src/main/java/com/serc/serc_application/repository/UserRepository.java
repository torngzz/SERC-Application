package com.serc.serc_application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serc.serc_application.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
    Optional<UserModel> findByUsername(String username);
    
}
