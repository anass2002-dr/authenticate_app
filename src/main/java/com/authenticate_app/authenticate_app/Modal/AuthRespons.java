package com.authenticate_app.authenticate_app.Modal;

import lombok.Data;

import javax.management.ConstructorParameters;


public class AuthRespons {
    private String Token;
    private String Message;

    public AuthRespons(String token, String message) {
        this.Token = token;
        this.Message = message;
    }

    public AuthRespons() {
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
