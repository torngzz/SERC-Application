package com.aub.backend_aub_shop.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long product_id;

    @Column(name = "pro_name")
    private String productName;

    // @Column(name = "original_price")
    private Double original_price;

    // @Column(name = "sale_price")    
    private Double sale_price;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    // private MultipartFile image_url;
    // private String createByUsername;
    // private String updateByUsername;

    private String description;

    private String detailImageUrl; 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id") // Consider EAGER for smaller categories
    private Category category;
    // @ManyToOne
    // @JoinColumn(name = "category_id")
    // private Category category;
   
    // private String createbyUsername;

   
    // @Column(name = "image")
    private String image_url;

    // @Column(name = "created_date")
    @DateTimeFormat
    private Date created_date;

    // @Column(name = "created_by")
    private Long created_by;
    private Long updateBy;
    @DateTimeFormat
    private Date updated_date;


    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    // public String getCreateByUsername() {
    //     return createByUsername;
    // }

    // public void setCreateByUsername(String createByUsername) {
    //     this.createByUsername = createByUsername;
    // }

    // public String getUpdateByUsername() {
    //     return updateByUsername;
    // }

    // public void setUpdateByUsername(String updateByUsername) {
    //     this.updateByUsername = updateByUsername;
    // }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product()
    {

    }

    public Product(String name, double price, Long category, String image, String description) {

        this.productName = name;
        this.sale_price = price;
        this.categoryId = category;
        this.detailImageUrl = image;
        this.description = description;
    }

    public String getProductDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Product ID: ").append(product_id).append("\n");
        details.append("Name: ").append(productName).append("\n");
        details.append("Category: ").append(categoryId).append("\n");
        details.append("Price: ").append(sale_price).append("\n");
        details.append("Description: ").append(description).append("\n");
        return details.toString();
    }

    // public void setImage_url(MultipartFile image)
    // {
    //     this.image_url = image;
    // }
    // public MultipartFile getImage_url()
    // {
    //     return image_url;
    // }

    public String getPro_name() {
        return productName;
    }

    public void setPro_name(String productName) {
        this.productName = productName;
    }

    public String getDetailImage() {
        return detailImageUrl;
    }
    
    public void setDetailImage(String de) {
        this.detailImageUrl = de;
    }
    

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String des) {
        this.description = des;
    }

    public String getImage_url() {
        return image_url;
    }
    
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setCategory_id(Long category_id) {
        this.categoryId = category_id;
    }

    public Long getCategory_id() {
        return categoryId;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(Long created_by) {
        this.created_by = created_by;
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

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

 
    public String getDetailImageUrl() {
        return detailImageUrl;
    }

    public void setDetailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

   

   
}
