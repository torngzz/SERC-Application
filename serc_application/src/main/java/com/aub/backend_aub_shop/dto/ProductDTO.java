package com.aub.backend_aub_shop.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.aub.backend_aub_shop.model.Category;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductDTO {
    private Long product_id;

  
    private String productName;
    // @Column(name = "original_price")
    private Double original_price;

    // @Column(name = "sale_price")    
    private Double sale_price;

    // @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    // private MultipartFile image_url;
    // @Column(name = "image")
    private String image_url;

    // @Column(name = "created_date")
    @DateTimeFormat
    private Date created_date;

    private String description;

    private String detailImageUrl; 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id") // Consider EAGER for smaller categories
    private Category category;
    private Long created_by; 
    private Long updateBy;
    private String createByUsername;
    private String updateByUsername;
    @DateTimeFormat
    private Date updated_date;

    public Date getUpdated_date() {
        return updated_date;
    }
    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }
    public Long getProduct_id() {
        return product_id;
    }
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Double getOriginal_price() {
        return original_price;
    }
    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }
    public Double getSale_price() {
        return sale_price;
    }
    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    public Date getCreated_date() {
        return created_date;
    }
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDetailImageUrl() {
        return detailImageUrl;
    }
    public void setDetailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public Long getCreated_by() {
        return created_by;
    }
    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
    }
    public Long getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
    public String getCreateByUsername() {
        return createByUsername;
    }
    public void setCreateByUsername(String createByUsername) {
        this.createByUsername = createByUsername;
    }
    public String getUpdateByUsername() {
        return updateByUsername;
    }
    public void setUpdateByUsername(String updateByUsername) {
        this.updateByUsername = updateByUsername;
    }
    

}
