package com.example.healthcare;

public class CardData {

    private String  Specialization,email,fullname,image_url;

    public CardData() {
    }

    public CardData(String specialization, String email, String fullname, String image_url) {
        Specialization = specialization;
        this.email = email;
        this.fullname = fullname;
        this.image_url = image_url;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
