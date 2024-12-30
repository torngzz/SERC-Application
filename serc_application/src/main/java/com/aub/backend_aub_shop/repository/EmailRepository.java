package com.aub.backend_aub_shop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.EmailModel;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Long>{
    @Modifying
    // @Query("UPDATE EmailModel e SET e.email = :email WHERE e.contact = :contact")
    @Query(value = "UPDATE EmailModel e SET e.email = :email " +
           "WHERE e.contact.id IN (SELECT c.id FROM ContactModel c WHERE c.people.id = :peopleId)")
    void updateEmailForPeople(@Param("peopleId") UUID peopleId, @Param("email") String email);
}