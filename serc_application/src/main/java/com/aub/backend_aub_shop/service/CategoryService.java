package com.aub.backend_aub_shop.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.dto.CategoryDTO;
import com.aub.backend_aub_shop.model.Category;
import com.aub.backend_aub_shop.model.Product;
import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.repository.CategoryRepository;
import com.aub.backend_aub_shop.repository.UserRepository;
import com.aub.backend_aub_shop.util.UserSessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service("categoryService")
public class CategoryService {
    @Autowired CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;  // Assuming this service has a method to get a username by ID


    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

/**
 * 
 * @param cate_name
 * @param pageNumber
 * @param pageSize
 * @return
 */
public Page<CategoryDTO> findAll(String cate_name, int pageNumber, int pageSize) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);

    // Product pro = new Product();
    //     if (cate_name == null || cate_name.trim().isEmpty()) {
    //         return categoryRepository.findAll(pageable);
    //     }
    //     return categoryRepository.findByCategoryNameContainingIgnoreCase(cate_name, pageable);
        Page<Category> category = categoryRepository.findByCategoryNameContainingIgnoreCase(cate_name, pageable);
        return category.map(cate -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(cate.getId());
            dto.setCategoryName(cate.getCategoryName());
            dto.setDescription(cate.getDescription());
            dto.setCreated_by(getUsernameById(cate.getCreated_by()));
            dto.setCreated_date(cate.getCreated_date());
            dto.setUpdatedBy(getUsernameById(cate.getUpdated_by()));
            dto.setUpdate_date(cate.getUpdate_date());
            return dto;
        });
    }

    @Autowired private UserRepository userRepository;
    public String getUsernameById(Long userId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getUsername();
    }

    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

   public Category saveCategory(Category category ,HttpServletRequest request){
     HttpSession session = request.getSession();
     Long sessionId = UserSessionUtils.getUserId(session);
     if (sessionId == null) {
        throw new IllegalStateException("User ID not found in session. Cannot create user.");
    }
    // return categoryRepository.save(category);
    try { 
        LOGGER.info("My Category: "  + category.toString());
        Date d = new Date();
        category.setCreated_date(d);
        category.setUpdate_date(d);
        category.setCreated_by(sessionId);
        category.setUpdatedBy(sessionId);
        return categoryRepository.save(category);
    } catch (Exception e) {
        LOGGER.error(" System error", e);
        return categoryRepository.save(category);
    }
   }

   public void deleteById(Long id){
    categoryRepository.deleteById(id);
   }
   
   public Category updateCategory(Category category, Long id ,HttpServletRequest request){
  
       Optional<Category> optionalCategory = categoryRepository.findById(id);

       HttpSession session = request.getSession();
       Long sessionId = UserSessionUtils.getUserId(session);
       if (sessionId == null) {
          throw new IllegalStateException("User ID not found in session. Cannot create user.");
        }

        try
        { 
            LOGGER.info("My Category: "  + category.toString());
            Date d = new Date();
            category.setCreated_date(d);
            return categoryRepository.save(category);
        } catch (Exception e) {
            LOGGER.error(" System error", e);
        if (optionalCategory.isPresent()){
                Category ca = optionalCategory.get();
                ca.setCategoryName(category.getCategoryName());
                ca.setDescription(category.getDescription());
                // ca.setCreated_date(category.getCreated_date());
                // ca.setCreated_by(category.getCreated_by());

                ca.setUpdate_date(new Date());              
                ca.setUpdatedBy(sessionId);
        
                return categoryRepository.save(ca);
        }
        return null;
        }

    }
}
