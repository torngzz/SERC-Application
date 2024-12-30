package com.aub.backend_aub_shop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.PhoneModel;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneModel, Long>{
    @Modifying
    @Query("UPDATE PhoneModel p SET p.phone = :phone " +
           "WHERE p.contact.id IN (SELECT c.id FROM ContactModel c WHERE c.people.id = :peopleId)")
    void updatePhoneForPeople(@Param("peopleId") UUID peopleId, @Param("phone") String phone);
}
