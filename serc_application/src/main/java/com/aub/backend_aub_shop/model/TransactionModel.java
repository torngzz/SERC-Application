package com.aub.backend_aub_shop.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TBL_TRANSACTIONLOG")
@EntityListeners(AuditingEntityListener.class)
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String role;
    private LocalDateTime loginDate;
    private String action;
    private String status;
    private LocalDateTime logoutDate;

    public LocalDateTime getLoginDate() {
        return loginDate;
    }
    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getLogoutDate() {
        return logoutDate;
    }
    public void setLogoutDate(LocalDateTime logoutDate) {
        this.logoutDate = logoutDate;
    }
}
