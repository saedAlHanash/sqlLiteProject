package com.example.sqlliteproject.DataBases.Models;

public class Owner {
    public int id;
    public String name;
    public String description;

    public Owner(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Owner() {
    }
}

