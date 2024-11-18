package com.aub.backend_aub_shop.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private String username;
    private String role;
    private LocalDateTime loginDate;
    private String action;
    private String status;
    private LocalDateTime logoutDate;

    public LocalDateTime getLogoutDate() {
        return logoutDate;
    }
    public void setLogoutDate(LocalDateTime logouDate) {
        this.logoutDate = logouDate;
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
    public LocalDateTime getLoginDate() {
        return loginDate;
    }
    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
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

}
