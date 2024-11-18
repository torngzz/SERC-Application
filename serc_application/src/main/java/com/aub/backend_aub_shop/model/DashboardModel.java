package com.aub.backend_aub_shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DashboardModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    public Long id;
    public Long totalusers;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTotalusers() {
        return totalusers;
    }
    public void setTotalusers(Long totalusers) {
        this.totalusers = totalusers;
    }

}
