package com.aub.backend_aub_shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aub.backend_aub_shop.model.EmailModel;
import com.aub.backend_aub_shop.model.MediaModel;
import com.aub.backend_aub_shop.model.PeopleModel;
import com.aub.backend_aub_shop.model.PhoneModel;
import com.aub.backend_aub_shop.service.PeopleService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"/people"})
public class PeopleController {

    @Autowired private PeopleService peopleService;
    private static final String UPLOAD_DIR = "C:/shared/serc_images/";
    private static final Logger LOGGER = LoggerFactory.getLogger(PeopleModel.class);

    @GetMapping(value={"", "/getPeople"})
    public String getAllPeople(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "name_en", required = false, defaultValue = "") String name,
            Model model
    ){
        Page<PeopleModel> people = peopleService.findAll(name, pageNumber, pageSize);
        model.addAttribute("people", people);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", people.getTotalPages());
        return "UserManagement/People/people-list";
    }

    @GetMapping("/addPeople")
    public String addPeople(Model m)
    {
        m.addAttribute("people", new PeopleModel());
        return "UserManagement/People/add-people"; 
    }
    
    @PostMapping("/savePeople")
    public String savePeople(
            @ModelAttribute("people") PeopleModel peopleModel,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "currentImage", required = false) String currentImage,
            @RequestParam(value = "phones", required = false) List<String> phoneNumbers,
            @RequestParam(value = "emails", required = false) List<String> emailAddresses,
            @RequestParam(value = "medias", required = false) List<String> mediaUrls,
            RedirectAttributes redirectAttributes,
            HttpServletRequest httpRequest,
            Model model) {

        try {
            // Process uploaded file
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.write(filePath, file.getBytes());
                peopleModel.setImage(fileName); // Save file name
            } else if (currentImage != null) {
                peopleModel.setImage(currentImage); // Keep existing image
            }

            // Prepare related models
            List<PhoneModel> phones = phoneNumbers != null
                    ? phoneNumbers.stream().map(phone -> {
                      PhoneModel phoneModel = new PhoneModel();
                      phoneModel.setPhone(phone);
                      return phoneModel;
                    }).collect(Collectors.toList())
                    : new ArrayList<>();
                    LOGGER.info("Phones: {}", phoneNumbers);

            List<EmailModel> emails = emailAddresses != null
                    ? emailAddresses.stream().map(email -> {
                        EmailModel emailModel = new EmailModel();
                        emailModel.setEmail(email);
                        return emailModel;
                    }).collect(Collectors.toList())
                    : new ArrayList<>();
                    LOGGER.info("Email: {}", emailAddresses);

            List<MediaModel> medias = mediaUrls != null
                    ? mediaUrls.stream().map(media -> {
                        MediaModel mediaModel = new MediaModel();
                        mediaModel.setMedia(media);
                        return mediaModel;
                    }).collect(Collectors.toList())
                    : new ArrayList<>();

            // Save PeopleModel along with related data
            peopleService.create(httpRequest, peopleModel, phones, emails, medias);

            redirectAttributes.addFlashAttribute("successMessage", "Person saved successfully!");
            return "redirect:/people/getPeople";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            LOGGER.error("Error saving person: {}", e.getMessage(), e);
            return "UserManagement/People/people-list";
        }
    }

    @GetMapping("/viewPeople/{id}")
    public String viewPeople(@PathVariable("id")UUID id, Model m)
    {
        Optional<PeopleModel> people = peopleService.findbyId(id);
        if (people.isEmpty()) {
            // Redirect to an error page or list page if the ID is invalid
            m.addAttribute("error", "The requested person was not found.");
            return "UserManagement/People/error";
        }
        // Dummy data for dynamic properties
        List<String> emails = Arrays.asList("example1@gmail.com", "example2@gmail.com");
        List<String> phones = Arrays.asList("123456789", "987654321");
        List<String> medias = Arrays.asList("Facebook", "LinkedIn");

        // Add attributes to the model
        m.addAttribute("people", people);
        m.addAttribute("emails", emails);
        m.addAttribute("phones", phones);
        m.addAttribute("medias", medias);
        m.addAttribute("people", people.orElse(new PeopleModel()));
        return "UserManagement/People/people-detail";

    }

    @GetMapping("/editPeople/{id}")
    public String editPeople(@PathVariable("id")UUID id, Model m)
    {
        Optional<PeopleModel> people = peopleService.findbyId(id);
        if (people.isEmpty()) {
            // Redirect to an error page or list page if the ID is invalid
            m.addAttribute("error", "The requested person was not found.");
            return "UserManagement/People/error";
        }
        // Dummy data for dynamic properties
        List<String> emails = Arrays.asList("example1@gmail.com", "example2@gmail.com");
        List<String> phones = Arrays.asList("123456789", "987654321");
        List<String> medias = Arrays.asList("Facebook", "LinkedIn");

        // Add attributes to the model
        m.addAttribute("people", people);
        m.addAttribute("emails", emails);
        m.addAttribute("phones", phones);
        m.addAttribute("medias", medias);
        m.addAttribute("people", people.orElse(new PeopleModel()));
        return "UserManagement/People/edit-people";

    }

    @PostMapping("/editPeople/{id}")
    public String editPeople(@PathVariable("id") UUID peopleId,
                             Model m,
                             @ModelAttribute("people") PeopleModel people,
                             @RequestParam("image") MultipartFile file,
                             HttpServletRequest httprequest) throws IOException

    {
        Optional<PeopleModel> p = peopleService.findbyId(peopleId);
        if(p.isPresent()){
            PeopleModel existingPeople = p.get();
            
            if(!file.isEmpty()){
                try {
                    String fileName = file.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR, fileName);
                    Files.write(filePath, file.getBytes());
                    people.setImage(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    m.addAttribute("errorMessage","Error updating image.");
                    return "redirect:/people/edit-people";
                }
            }
            else{
                people.setImage(existingPeople.getImage());
            }
            PeopleModel editPeople = peopleService.editPeople(people, peopleId, httprequest);
            m.addAttribute("people", editPeople);

            return "redirect:/people/people-list";
        }else{
            m.addAttribute("errorMessage", "People not found.");
            return "redirect:/people/people-list";
        }
    }
  
}
