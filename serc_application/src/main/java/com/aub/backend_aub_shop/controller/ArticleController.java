package com.aub.backend_aub_shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;

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

import com.aub.backend_aub_shop.model.ArticleModel;
import com.aub.backend_aub_shop.service.ArticleService;

@Controller
@RequestMapping("/Article")
public class ArticleController {
    @Autowired 
    private ArticleService articleService;
    private static final String UPLOAD_DIR = "C:/shared/images/";

    @GetMapping("/list")
    public String get(
       @RequestParam(name = "pageNumber", defaultValue= "0") int pageNumber,
       @RequestParam(name = "pageSize", defaultValue= "5") int pageSize,
       Model model) {
        Page<ArticleModel> articles = articleService.findAll(pageNumber, pageSize);
        model.addAttribute("articles", articles);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", articles.getTotalPages());
        return "Article/article-form"; // Corrected to article-list
    }

    @GetMapping("/addArticle")
    public String showAddArticleForm(Model model) {
        model.addAttribute("article", new ArticleModel());
        return "Article/add-article";
    }

    @PostMapping("/add")
public String addArticle(@ModelAttribute ArticleModel article,
                         Principal principal,
                         @RequestParam("image") MultipartFile file) {
                            // Save main image
        try {
            // Save main image
            Path fileNameAndPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            article.setImageUrl(file.getOriginalFilename()); // Set imageUrl as filename
    
            article.setCreatedDate(new Date()); // Automatically set current date
            article.setCreatedBy(principal != null ? principal.getName() : "Anonymous"); // Set to current user or "Anonymous"
            articleService.save(article);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception, you might want to log this or show a message
            return "redirect:/Article/add-article?error"; // Redirect to the add article page with an error flag
        }
    
    return "redirect:/Article/list"; // Redirect to the article list page
    // article.setCreatedDate(new Date()); // Automatically set current date
    // article.setCreatedBy(principal != null ? principal.getName() : "Anonymous"); // Set to current user or "Anonymous"
    // articleService.save(article);
    // return "redirect:/Article/list"; // Redirect to the article list page
}


    @GetMapping("/details/{id}")
    public String getArticleDetails(@PathVariable("id") Long id, Model model) {
        ArticleModel article = articleService.findById(id);
        model.addAttribute("article", article);
        return "Article/article-details";
    }

    @GetMapping("/edit/{id}")
    public String showEditArticleForm(@PathVariable("id") Long id, Model model) {
        ArticleModel article = articleService.findById(id);
        model.addAttribute("article", article);
        return "Article/edit-article";
    }
    @PostMapping("/update/{id}")
    public String updateArticle(@PathVariable("id") Long id,
                                @ModelAttribute ArticleModel article,
                                @RequestParam("image") MultipartFile file) {
        // Fetch the existing article to ensure `createdDate` and `imageUrl` are not overwritten
        ArticleModel existingArticle = articleService.findById(id);
        
        article.setCreatedDate(existingArticle.getCreatedDate()); // Preserve existing date
        article.setCreatedBy(existingArticle.getCreatedBy()); // Preserve existing user

        // Check if a new image was uploaded
        if (!file.isEmpty()) {
            try {
                // Save the new image
                Path fileNameAndPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
                Files.write(fileNameAndPath, file.getBytes());
                article.setImageUrl(file.getOriginalFilename()); // Update the image URL with the new filename
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/Article/edit-article"; // Handle the error gracefully
            }
        } else {
            // Retain the existing image URL if no new file is uploaded
            article.setImageUrl(existingArticle.getImageUrl());
        }

        article.setId(id); // Set the ID for update
        articleService.save(article);
        return "redirect:/Article/list";
    }
    
//     @PostMapping("/update/{id}")
// public String updateArticle(@PathVariable("id") Long id, @ModelAttribute ArticleModel article) {
//     // Fetch the existing article to ensure `createdDate` is not overwritten
//     ArticleModel existingArticle = articleService.findById(id);
//     article.setCreatedDate(existingArticle.getCreatedDate()); // Preserve existing date
//     article.setCreatedBy(existingArticle.getCreatedBy()); // Preserve existing user
//     article.setId(id); // Set the ID for update
//     articleService.save(article);
//     return "redirect:/Article/list";
// }


    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteById(id);
        return "redirect:/Article/list";
    }
}
