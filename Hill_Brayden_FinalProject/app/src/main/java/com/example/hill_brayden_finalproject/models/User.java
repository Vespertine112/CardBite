package com.example.hill_brayden_finalproject.models;

import java.net.URI;

public class User{
    String UID;
    String name;
    String email;
    String phone;
    String role;
    String facebook;
    String instagram;
    String linkedin;
    String twitter;

    public User(String UID, String name, String email, String phone,String role, String facebook, String instagram, String linkedin, String twitter) {
        this.UID = UID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.facebook = facebook;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.twitter = twitter;
    }

    public User() {
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
