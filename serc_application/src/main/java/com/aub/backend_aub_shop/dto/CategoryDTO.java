package com.aub.backend_aub_shop.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.aub.backend_aub_shop.model.Product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public class CategoryDTO {
    private Long id;
    private String categoryName;
    private String description;
    // @DateTimeFormat
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_date;
    private String created_by;
    private String updated_by;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date update_date;
 
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products;


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCategoryName()
    {
        return categoryName;
    }
    public void setCategoryName(String categoryname)
    {
        this.categoryName = categoryname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    public String getUpdated_by(){
        return updated_by;
    }
    public void setUpdatedBy(String updated_by){
       this.updated_by = updated_by;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

  
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + categoryName + ", description=" + description + ", created_date="
                + created_date + ", created_by=" + created_by + ", products=" + products + "]";
    }
}

