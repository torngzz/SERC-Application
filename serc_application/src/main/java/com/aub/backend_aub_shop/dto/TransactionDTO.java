package com.aub.backend_aub_shop.dto;

import java.util.Date;

import com.aub.backend_aub_shop.util.LogAction;

public class TransactionDTO {
    private Long id;
    private String username;
    private String role;
    private Date loginDate;
    private LogAction action;
    private String status;
    private Date logoutDate;

    public Date getLogoutDate() {
        return logoutDate;
    }
    public void setLogoutDate(Date logouDate) {
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
    public Date getLoginDate() {
        return loginDate;
    }
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
    public LogAction getAction() {
        return action;
    }
    public void setAction(LogAction action) {
        this.action = action;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
