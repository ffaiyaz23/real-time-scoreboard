package com.example.user_service.dto;

public class AuthResponse {
    private String token;

    // Constructor accepting a token argument
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter and setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
