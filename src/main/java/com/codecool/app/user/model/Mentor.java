package com.codecool.app.user.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Mentor {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public Mentor(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Class> getClassess(){

        throw new NotImplementedException();
    }
}
