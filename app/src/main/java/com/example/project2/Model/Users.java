package com.example.project2.Model;

public class Users {
    private String name, number, password, image;

    public Users(){

    }

    public Users(String name, String number, String password, String image) {
        this.name = name;
        this.number = number;
        this.password = password;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String password) {
        this.image = image;
    }
}
