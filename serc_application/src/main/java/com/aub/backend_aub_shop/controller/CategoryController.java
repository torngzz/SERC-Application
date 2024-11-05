package com.aub.backend_aub_shop.controller;

import java.util.Optional;

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

import com.aub.backend_aub_shop.dto.CategoryDTO;
import com.aub.backend_aub_shop.model.Category;
import com.aub.backend_aub_shop.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/categorys")
public class CategoryController {
    @Autowired CategoryService categoryService;

    //@GetMapping(value = {"","/"})
    // public String getAllCategory(
    //     // @RequestParam(name="pageNumber" , required=false, defaultValue="0") int pageNumber,
    //     // @RequestParam(name="pageSize", required=false, defaultValue="10") int pageSize,
    //     // @RequestParam(name="categoryName",required=false, defaultValue="") String categoryName,
    //     // Model m
    //     // ){
    //     // Page<Category> category = categoryService.findAll (categoryName, pageNumber, pageSize); 
    //     // m.addAttribute("category",category);
    //     // m.addAttribute("categoryName",categoryName);
    //     // return "/category/category-list";

    //     @RequestParam(name="pageNumber", required=false, defaultValue="0") int pageNumber,
    //     @RequestParam(name="pageSize", required=false, defaultValue="10") int pageSize,
    //     @RequestParam(name="categoryName", required=false, defaultValue="") String categoryName,
    //     Model m
    // ) {
    //     Page<Category> categoryPage = categoryService.findAll(categoryName, pageNumber, pageSize);
    //     m.addAttribute("categoryPage", categoryPage);
    //     m.addAttribute("categoryName", categoryName);
    //     m.addAttribute("currentPage", pageNumber);
    //     m.addAttribute("pageSize", pageSize);
    //     m.addAttribute("totalPages", categoryPage.getTotalPages());
    //     return "/category/category-list";
        
    // }
    
    @GetMapping(value = {"", "/"})
    public String getAllCategory(
        // List<Product> products = productService.findAll();
        // LOGGER.info("This is my product." + products.toString());
        // model.addAttribute("products", products);
        // model.addAttribute("categories", categoryService.getAllCategories());

        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
        @RequestParam(name=   "categoryName",required=false, defaultValue="") String categoryName,
        Model model
        )
    {
        Page<CategoryDTO> categoryPage = categoryService.findAll(categoryName, pageNumber, pageSize);
        model.addAttribute("categoryPage", categoryPage.toList());
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", categoryPage.getTotalPages());        
        
        return "/category/category-list";
    }


    @GetMapping("/addCategory")
    public String addCategory(Model m){
        m.addAttribute("category",new Category());
        return "/category/add-category";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("category") Category category, HttpServletRequest httprequest){
        categoryService.saveCategory(category,httprequest);
        return  "redirect:/categorys";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Long id, Model m){
        Optional<Category> categorys = categoryService.findById(id);
        m.addAttribute("category", categorys.orElse(new Category()));
        return "/category/edit-category";
    }

     @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id, @ModelAttribute("category") Category categorys , Model m , HttpServletRequest httprequest){
        Category updateCategory = categoryService.updateCategory(categorys, id,httprequest);
        m.addAttribute("category",updateCategory);
        return "redirect:/categorys";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "redirect:/categorys"; 
    } 
    
}
