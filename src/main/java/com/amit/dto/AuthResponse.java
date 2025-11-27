package com.amit.dto;

public class AuthResponse {
    
    private String token;
    private String nickname;
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(String token, String nickname, String message) {
        this.token = token;
        this.nickname = nickname;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
