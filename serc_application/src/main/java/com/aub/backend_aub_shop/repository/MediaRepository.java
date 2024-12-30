package com.aub.backend_aub_shop.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aub.backend_aub_shop.model.MediaModel;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Long>{
    @Modifying
    // @Query("UPDATE MediaModel m SET m.media = :media WHERE m.contact = :contact")
    @Query("UPDATE MediaModel m SET m.media = :media " +
           "WHERE m.contact.id IN (SELECT c.id FROM ContactModel c WHERE c.people.id = :peopleId)")
    void updateMediaForPeople(@Param("peopleId") UUID peopleId, @Param("media") String media);
}
