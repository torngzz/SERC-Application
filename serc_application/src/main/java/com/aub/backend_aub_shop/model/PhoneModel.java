package com.aub.backend_aub_shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "phone")
public class PhoneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "_phone_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contact_id", referencedColumnName="_contact_id", nullable=false)
    private ContactModel contact;

    private String phone;
    private boolean status;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
   
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public ContactModel getContact() {
        return contact;
    }
    public void setContact(ContactModel contact) {
        this.contact = contact;
    }
   
    

}
