package com.aub.backend_aub_shop.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

import com.aub.backend_aub_shop.dto.ProductDTO;
import com.aub.backend_aub_shop.model.Product;
import com.aub.backend_aub_shop.service.CategoryService;
import com.aub.backend_aub_shop.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

   

    // private static final String UPLOAD_DIR = "C:/Users/rithy/OneDrive/Documents/GitHub/AUB-Workspaces/backend-aub-shop/src/main/resources/static/uploads";
   

    @GetMapping(value = {"", "/"})
    public String getAllProducts(
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
        @RequestParam(name = "productName", required = false, defaultValue = "") String productName,
        Model m) {
            
        List<Product> products = productService.findByNameContainingIgnoreCase(productName, pageNumber, pageSize);
        Page<ProductDTO> pro = productService.findAll(productName, pageNumber, pageSize);
        pageNumber = pageNumber != 0 ? pageNumber : 1; // Set default to 1 if not provided
        int totalPages = pro.getTotalPages();
        
        // If totalPages is zero, we need to handle it
        if (totalPages == 0) {
            totalPages = 1; // Setting totalPages to 1 to handle empty pages scenario
        }
        
        m.addAttribute("products", products);
        m.addAttribute("productDto", pro.toList());
        m.addAttribute("productName", productName);
        // m.addAttribute("pageNumber", pageNumber);
        m.addAttribute("pageSize", pageSize);
        m.addAttribute("totalPages", totalPages);
        m.addAttribute("currentPage", pageNumber);
        logger.info("This is my product. Total pages: " + totalPages);
        return "product-list";
    }


    @GetMapping("/addProduct")
    public String addProduct(Model m) {
        m.addAttribute("product", new Product());
        m.addAttribute("categories", categoryService.getAllCategories());
        return "add-product";
    }

    private static final String UPLOAD_DIR = "C:/shared/images/";

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("image") MultipartFile file,
                              @RequestParam("detailImages") MultipartFile[] detailImages,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest httprequest) throws IOException {
    
        // Save main image
        Path fileNameAndPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        product.setImage_url(file.getOriginalFilename());
    
        // Save detail images
        StringBuilder detailImageUrls = new StringBuilder();
        for (MultipartFile detailImage : detailImages) {
            if (!detailImage.isEmpty()) {
                Path detailImagePath = Paths.get(UPLOAD_DIR, detailImage.getOriginalFilename());
                Files.write(detailImagePath, detailImage.getBytes());
                detailImageUrls.append(detailImage.getOriginalFilename()).append(",");
            }
        }
    
        // Remove the trailing comma
        if (detailImageUrls.length() > 0) {
            detailImageUrls.setLength(detailImageUrls.length() - 1);
        }
    
        product.setDetailImageUrl(detailImageUrls.toString());
    
        // Save the product
        productService.saveProduct(product, httprequest);
    
        return "redirect:/products";
    }
    
    @GetMapping("/view/{id}")
    public String viewproduct(@PathVariable("id") Long id, Model m){
        Optional<Product> product = productService.findById(id);
        Product products = product.get();
        String[] detailImageUrl = products.getDetailImageUrl().split(",");
        m.addAttribute("detailImageUrls", detailImageUrl); 
        m.addAttribute("product", product.orElse(new Product()));
        m.addAttribute("categories", categoryService.getAllCategories());

        return "view-product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, 
                              Model m) {

        Optional<Product> product = productService.findById(id);
        Product products = product.get();        

        String[] detailImageUrl = products.getDetailImageUrl().split(",");
        m.addAttribute("detailImageUrls", detailImageUrl); 
        m.addAttribute("product", product.orElse(new Product()));
        m.addAttribute("categories", categoryService.getAllCategories());
        return "edit-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long productId,
                                @ModelAttribute("product") Product product,
                                @RequestParam("image") MultipartFile file,
                                @RequestParam(value = "detailImages", required = false) MultipartFile[] detailImages,
                                Model m,
                                HttpServletRequest httprequest) throws IOException {
    
        Optional<Product> pro = productService.findById(productId);
        if (pro.isPresent()) {
            Product existingProduct = pro.get();
    
            // Handle the main image upload
            if (!file.isEmpty()) {
                try {
                    String fileName = file.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR, fileName);
                    Files.write(filePath, file.getBytes());
                    product.setImage_url(fileName); // Set the new image URL
                } catch (IOException e) {
                    e.printStackTrace();
                    m.addAttribute("errorMessage", "Error saving the file.");
                    return "redirect:/products/edit-product"; // Redirect to edit page with an error
                }
            } else {
                // Retain the existing image URL if no new file is uploaded
                product.setImage_url(existingProduct.getImage_url());
            }
    
            // Handle detail images
            StringBuilder detailImageUrls = new StringBuilder();
            if (existingProduct.getDetailImageUrl() != null) {
                detailImageUrls.append(existingProduct.getDetailImageUrl());
                if (detailImages != null && detailImages.length > 0) {
                    detailImageUrls.append(",");
                }
            }
    
            if (detailImages != null && detailImages.length > 0) {
                for (MultipartFile detailImage : detailImages) {
                    if (!detailImage.isEmpty()) {
                        String detailFileName = detailImage.getOriginalFilename();
                        Path detailImagePath = Paths.get(UPLOAD_DIR, detailFileName);
                        Files.write(detailImagePath, detailImage.getBytes());
                        detailImageUrls.append(detailFileName).append(",");
                    }
                }
                if (detailImageUrls.length() > 0) {
                    detailImageUrls.setLength(detailImageUrls.length() - 1); // Remove the trailing comma
                }
            }
            
            product.setDetailImageUrl(detailImageUrls.toString());
    
            // Update the product in the database
            Product updatedProduct = productService.updateProduct(product, productId, httprequest);
            m.addAttribute("product", updatedProduct);
    
            return "redirect:/products";
        } else {
            // Handle the case where the product is not found
            m.addAttribute("errorMessage", "Product not found.");
            return "redirect:/products";
        }
    }
    

    

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model m) {
        Optional<Product> product = productService.findById(id);
        Product products = product.get();
        String[] detailImageUrl = products.getDetailImageUrl().split(",");
        m.addAttribute("detailImageUrls", detailImageUrl); 
        m.addAttribute("product", product.orElse(new Product()));
        m.addAttribute("categories", categoryService.getAllCategories());
        return "delete-product";
    }
    
    // @GetMapping("/delete/{id}")
    // public String delete(@RequestParam("productId") Long productId, Model m) {
    //     if (productService.findById(productId).isPresent()) {
    //         productService.deleteById(productId);
    //     } else {
    //         System.out.println("Product with ID " + productId + " not found.");
    //     }
    //     return "redirect:/products";
    // }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("productId") Long productId) {
        productService.deleteProductById(productId);
        logger.info("Product with ID " + productId + " deleted successfully.");
        return "redirect:/products";
    }


    // @PostMapping("/delete")
    // public String deleteProduct(@PathVariable Long id) {
    //     productService.deleteProductById(id);
    //     logger.info("Product with ID " + id + " deleted successfully.");
    //     return "redirect:/products"; 
    // }
     
}
