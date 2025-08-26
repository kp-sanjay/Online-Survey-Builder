package com.examly.springapp.dto;

public class AuthDtos {
    public static class RegisterRequest {
        public String fullName;
        public String email;
        public String password;
    }

    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public String token;
        public String email;
        public String fullName;

        public AuthResponse(String token, String email, String fullName) {
            this.token = token;
            this.email = email;
            this.fullName = fullName;
        }
    }
}


