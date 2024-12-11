package com.aub.backend_aub_shop.model;

import java.util.UUID;

public class UserSessionManager {
    private UUID userId;
    private String username;
    public UserSessionManager(){}
    public UserSessionManager(UUID userId, String username){
        this.userId = userId;
        this.username = username;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}