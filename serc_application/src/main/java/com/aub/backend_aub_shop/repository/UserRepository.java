package com.aub.backend_aub_shop.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByPhone(String phone);

    Page<UserModel> findByUsernameContaining(String username, Pageable pageable);

    Optional<UserModel> findByUsernameOrEmailOrPhoneAndIdNot(String username, String email, String phone, UUID id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT u.status FROM UserModel u WHERE u.status = 1 AND u.username = :username")
    int checkStatus(@Param("username") String username);

    @Query("SELECT COUNT(u) FROM UserModel u")
    int countUsers();
    
    // @Query(value = "SELECT * FROM tbl_user WHERE id = UUID_TO_BIN(:id)", nativeQuery = true)
    // UserModel findByIdWithUuidToBin(@Param("id") UUID id);
    // @Query(value = "SELECT * FROM tbl_user WHERE id = UUID_TO_BIN(:id, 1)", nativeQuery = true)
    // UserModel findByIdWithUuidToBin(@Param("id") UUID id);
    @Query(value = "SELECT * FROM tbl_user WHERE id = :id", nativeQuery = true)
    UserModel findByIdWithBinaryUuid(@Param("id") byte[] id);


}