package com.aub.backend_aub_shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.UserModel;

@Repository
public interface UserRepositoryCustom extends JpaRepository<UserModel, Long> {
  @Query(value = "SELECT " +
                   "A.ID AS id, " +
                   "A.USERNAME AS username, " +
                   "A.ROLE AS role, " +
                   "B.USERNAME AS createdByUsername, " +
                   "A.CREATED_DATE AS createdDate, " +
                   "C.USERNAME AS updatedByUsername, " +
                   "A.UPDATED_DATE AS updatedDate " +
                   "FROM TBL_USER A " +
                   "LEFT JOIN TBL_USER B ON B.ID = A.CREATED_BY " +
                   "LEFT JOIN TBL_USER C ON C.ID = A.UPDATED_BY " +
                   "WHERE A.USERNAME LIKE %:username%",
           countQuery = "SELECT COUNT(*) FROM TBL_USER A " +
                        "LEFT JOIN TBL_USER B ON B.ID = A.CREATED_BY " +
                        "LEFT JOIN TBL_USER C ON C.ID = A.UPDATED_BY " +
                        "WHERE A.USERNAME LIKE %:username%",
           nativeQuery = true)
    Page<UserModel> findUserDetails(@Param("username") String username, Pageable pageable);
}
