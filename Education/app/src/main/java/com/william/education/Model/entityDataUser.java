package com.william.education.Model;

public class entityDataUser {
    private String Email;
    private String Name;
    private String Image;

    public entityDataUser() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public entityDataUser(String email, String name, String image) {
        Email = email;
        Name = name;
        Image = image;
    }

}
