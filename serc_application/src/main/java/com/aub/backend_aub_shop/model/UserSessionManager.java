package com.aub.backend_aub_shop.model;

public class UserSessionManager {
    private Long userId;
    private String username;
    public UserSessionManager(){}
    public UserSessionManager(Long userId, String username){
        this.userId = userId;
        this.username = username;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}