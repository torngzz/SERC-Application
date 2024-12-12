package com.aub.backend_aub_shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aub.backend_aub_shop.model.PeopleModel;
import com.aub.backend_aub_shop.service.PeopleService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"", "/people"})
public class PeopleController {

    @Autowired private PeopleService peopleService;
    private static final String UPLOAD_DIR = "C:/shared/serc_images/";

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

    @PostMapping("/save")
    public String savePeople(
            @ModelAttribute("people") PeopleModel peopleModel,  // Non-file fields
            @RequestParam("image") MultipartFile file,          // File field
            @RequestParam("currentImage") String currentImage,
            HttpServletRequest httpRequest,
            Model model) throws IOException {

                // Process the file if it exists
            if (file != null && !file.isEmpty()) {
                try {
                    // Save the file to a directory (e.g., "uploads/")
                    String fileName = file.getOriginalFilename();
                    // String uploadDir = httpRequest.getServletContext().getRealPath("/uploads/");
                    
                    Path filePath = Paths.get("uploads", fileName); // Adjust the path as needed
                    Files.createDirectories(filePath.getParent()); // Ensure the directory exists
                    Files.write(filePath, file.getBytes());

                    // Save the file name to the model
                    peopleModel.setImage(fileName);
                } catch (IOException e) {
                    model.addAttribute("errorMessage", "Failed to upload file: " + e.getMessage());
                    return "UserManagement/People/people-list"; // Return to the form with an error
        
                }
            } 
            else {
                // Use the current image if no new file is uploaded
                peopleModel.setImage(currentImage);
            }
        // Process the file
        // if (file != null && !file.isEmpty()) {
        //     String fileName = file.getOriginalFilename();
        //     Path filePath = Paths.get(UPLOAD_DIR, fileName);
        //     Files.write(filePath, file.getBytes());
        //     // Save the file name to the model
        //     peopleModel.setImage(fileName); // Assuming this is a String
        // }

        try {
            // Save the model
            peopleService.create(httpRequest, peopleModel);
            return "redirect:/people/getPeople";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "UserManagement/People/people-list";
        }
    }
            
    
}
