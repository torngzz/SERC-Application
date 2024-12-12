package com.aub.backend_aub_shop.service;


import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.model.PeopleModel;
import com.aub.backend_aub_shop.repository.PeopleRepository;
import com.aub.backend_aub_shop.util.UserSessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class PeopleService {
    @Autowired private PeopleRepository peopleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleService.class);

    public Page <PeopleModel> findAll (String name_en, int pageNumber, int pageSize ){
        return peopleRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public PeopleModel create(HttpServletRequest request, PeopleModel people)
    {
        checkForDuplicatePeople(people);
        HttpSession session = request.getSession();
        UUID userId = UserSessionUtils.getUserId(session);

        if (userId == null) {
            LOGGER.info("User ID not found in session. Cannot create user.");
        }
        try{
            LOGGER.info("Save people: " + people);
            people.setStatus(1);
            people.setCreated_date(new Date());
            people.setCreated_by(userId);
        } catch(Exception e){
            LOGGER.error("Error saving people", e);
            throw new RuntimeException("Error saving people!", e);
        }
       

        return peopleRepository.save(people);
    }

    //Used in Create Process
    private void checkForDuplicatePeople(PeopleModel p){
        if(peopleRepository.existsByNameEn(p.getNameEn())){
            throw new IllegalArgumentException("This name has already existed!");
        }
    }

}
