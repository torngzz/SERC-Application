package com.aub.backend_aub_shop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "email")
public class EmailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_email_id")
    private Long id;

    @Column(name = "email")
    private String email;
    
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "contact_id", referencedColumnName = "_contact_id", nullable = false)
    private ContactModel contact;

    @Column(name="status")
    private Boolean status;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ContactModel getContact() {
        return contact;
    }
    public void setContact(ContactModel contact) {
        this.contact = contact;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}
