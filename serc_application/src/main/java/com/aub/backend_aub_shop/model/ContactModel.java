package com.aub.backend_aub_shop.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="contact")
public class ContactModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_contact_id")
    private Long id;

    @OneToOne // This sets up the relationship
    @JoinColumn(name = "people_id", referencedColumnName = "_people_id", nullable = false) 
    private PeopleModel people; // Reference the PeopleModel
    
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<EmailModel> emails = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<MediaModel> medias = new ArrayList<>();

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<PhoneModel> phones = new ArrayList<>();

    public List<EmailModel> getEmails() {
        return emails;
    }
    public void setEmails(List<EmailModel> emails) {
        this.emails = emails;
    }
    public List<MediaModel> getMedias() {
        return medias;
    }
    public void setMedias(List<MediaModel> medias) {
        this.medias = medias;
    }
    public List<PhoneModel> getPhones() {
        return phones;
    }
    public void setPhones(List<PhoneModel> phones) {
        this.phones = phones;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public PeopleModel getPeople() {
        return people;
    }
    public void setPeople(PeopleModel people) {
        this.people = people;
    }
    
 


}
