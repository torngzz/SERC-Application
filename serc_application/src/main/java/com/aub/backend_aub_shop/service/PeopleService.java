package com.aub.backend_aub_shop.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.model.ContactModel;
import com.aub.backend_aub_shop.model.EmailModel;
import com.aub.backend_aub_shop.model.MediaModel;
import com.aub.backend_aub_shop.model.PeopleModel;
import com.aub.backend_aub_shop.model.PhoneModel;
import com.aub.backend_aub_shop.repository.ContactRepository;
import com.aub.backend_aub_shop.repository.EmailRepository;
import com.aub.backend_aub_shop.repository.MediaRepository;
import com.aub.backend_aub_shop.repository.PeopleRepository;
import com.aub.backend_aub_shop.repository.PhoneRepository;
import com.aub.backend_aub_shop.util.UserSessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class PeopleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleService.class);
    @Autowired private PeopleRepository peopleRepository;
    @Autowired ContactRepository contactRepository;
    @Autowired PhoneRepository phoneRepository;
    @Autowired EmailRepository emailRepository;
    @Autowired MediaRepository mediaRepository;
    
    ContactModel contact = new ContactModel();
    PhoneModel phone = new PhoneModel();
    EmailModel email = new EmailModel();
    MediaModel media = new MediaModel();
 
    public Page <PeopleModel> findAll (String name_en, int pageNumber, int pageSize ){
        return peopleRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Transactional
    public PeopleModel create(
        HttpServletRequest request,
        PeopleModel people,
        List<PhoneModel> phones,
        List<EmailModel> emails,
        List<MediaModel> medias) {
    
        checkForDuplicatePeople(people);
    
        HttpSession session = request.getSession();
        UUID userId = UserSessionUtils.getUserId(session);
        
        if (userId == null) {
            throw new IllegalArgumentException("User ID is required.");
        }
    
        if (people.getId() == null) {
            people.setId(UUID.randomUUID());
        }
        people.setStatus(1);
        people.setCreated_date(new Date());
        people.setCreated_by(userId);
        
        people = peopleRepository.save(people);
        contact.setPeople(people);

        // Associate and save PhoneModels
        if (phones != null) {
            phones.forEach(phone -> {
                phone.setStatus(true);
                phone.setContact(contact); // Set the contact
                contact.getPhones().add(phone); // Add phone to contact
            });
        }

        // Associate and save EmailModels
        if (emails != null) {
            emails.forEach(email -> {
                email.setStatus(true);
                email.setContact(contact); // Set the contact
                contact.getEmails().add(email); // Add email to contact
            });
        }

        // Associate and save MediaModels
        if (medias != null) {
            medias.forEach(media -> {
                media.setStatus(true);
                media.setContact(contact); // Set the contact
                contact.getMedias().add(media); // Add media to contact
            });
        }       
        
        // phone.setContact(contact);
        contact = contactRepository.save(contact);
        // Step 4: Update People with Contact ID
        people.setContact(contact);
        peopleRepository.save(people);

        return people;
    }
    

    //Used in Create Process
    private void checkForDuplicatePeople(PeopleModel p){
        if(peopleRepository.existsByNameEn(p.getNameEn())){
            throw new IllegalArgumentException("This name has already existed!");
        }
    }

    public Optional<PeopleModel> findbyId(UUID id){
        return peopleRepository.findById(id);
    }

    @Transactional
    public PeopleModel editPeople(PeopleModel p,
                                        UUID id,
                                        String email,
                                        String phone,
                                        String media,
                                        HttpServletRequest request) 
    {
        Optional<PeopleModel> people = peopleRepository.findById(id);
        HttpSession session = request.getSession();
        UUID userId = UserSessionUtils.getUserId(session);

        if (userId == null) {
            throw new IllegalStateException("User ID not found in session. Cannot update.");
        }
        if (people.isPresent()) {
            PeopleModel existPeople = people.get();

            // Update people fields
            existPeople.setNameEn(p.getNameEn());
            existPeople.setNameKh(p.getNameKh());
            existPeople.setDob(p.getDob());
            existPeople.setDivision(p.getDivision());
            existPeople.setGender(p.getGender());
            existPeople.setImage(p.getImage());
            existPeople.setIndividual_type(p.getIndividual_type());
            existPeople.setNationality(p.getNationality());
            existPeople.setTitle(p.getTitle());
            existPeople.setPassport(p.getPassport());
            existPeople.setContact(p.getContact());
            existPeople.setUpdated_by(userId);
            existPeople.setUpdated_date(new Date());

            // Update additional fields (email, phone, media)
            if (email != null) {
                // Update email in a related table or model
                emailRepository.updateEmailForPeople(id, email);
            }
            if (phone != null) {
                // Update phone in a related table or model
                phoneRepository.updatePhoneForPeople(id, phone);
            }
            if (media != null) {
                // Update media in a related table or model
                mediaRepository.updateMediaForPeople(id, media);
            }
            

            LOGGER.info("People updated successfully!", existPeople.toString());
            return peopleRepository.save(existPeople);
        } else {
            LOGGER.warn("People with ID: " + id + " not found for update.");
            return null;
        }
    }


}
