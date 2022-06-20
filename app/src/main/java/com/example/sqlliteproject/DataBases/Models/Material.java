package com.example.sqlliteproject.DataBases.Models;

public class Material {

    public String name;
    public int id;
    public String description;
    public boolean isService;

    public Material(String name, String description, boolean isService) {
        this.name = name;
        this.description = description;
        this.isService = isService;
    }

    public Material() {
    }
}

