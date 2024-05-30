package com.dto;

public class UserJson {
    private String username;
    private String email;
    private String password;
    private String profilePictureUrl;

    public UserJson() {
    };

    public UserJson(String username, String email, String password, String profilePictureUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getProfilePictureUrl() {
        return this.profilePictureUrl;
    }

}

/* Used to define the json body for requests */