package com.aub.backend_aub_shop.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import com.aub.backend_aub_shop.util.UUIDConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class PeopleModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Use UUID strategy for ID generation

    @Column(name = "_people_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    @Convert(converter = UUIDConverter.class) // Use custom converter
    private UUID id = UUID.randomUUID();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    private String division;
    private String gender;
    private String idCard;
    private String image;
    private String individual_type;
    private String nameEn;
    private String nameKh;
    private String nationality;  
    private String passport;
    private String role;
    private int status;
    private String title;
    private UUID created_by;
    private Date created_date;
    private UUID updated_by;
    private Date updated_date; 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", referencedColumnName = "_contact_id")
    private ContactModel contact;
        
    public ContactModel getContact() {
        return contact;
    }
    public void setContact(ContactModel contact) {
        this.contact = contact;
    }
    public UUID getCreated_by() {
        return created_by;
    }
    public void setCreated_by(UUID created_by) {
        this.created_by = created_by;
    }
    public Date getCreated_date() {
        return created_date;
    }
    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
    public UUID getUpdated_by() {
        return updated_by;
    }
    public void setUpdated_by(UUID updated_by) {
        this.updated_by = updated_by;
    }
    public Date getUpdated_date() {
        return updated_date;
    }
    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }  

    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Date getDob() {
        return dob;
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    public String getDivision() {
        return division;
    }
    public void setDivision(String division) {
        this.division = division;
    }
    
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getIndividual_type() {
        return individual_type;
    }
    public void setIndividual_type(String individual_type) {
        this.individual_type = individual_type;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(String name_en) {
        this.nameEn = name_en;
    }
    public String getNameKh() {
        return nameKh;
    }
    public void setNameKh(String name_kh) {
        this.nameKh = name_kh;
    }
    public String getPassport() {
        return passport;
    }
    public void setPassport(String passport) {
        this.passport = passport;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
  

}
