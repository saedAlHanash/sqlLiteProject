package com.example.sqlliteproject.DataBases.Models;

public class Outlay {

    public int materialId;
    public int ownerId;
    public int id;
    public float price;
    public int day;
    public int month;
    public int year;
    public String description;

    public Outlay(int materialId, int ownerId, float price, int day, int month, int year, String description) {
        this.materialId = materialId;
        this.ownerId = ownerId;
        this.price = price;
        this.day = day;
        this.year = year;
        this.month = month;
        this.description = description;
    }

    public Outlay(OutlayJoin outlayJoin) {
        this.id =outlayJoin.id;
        this.materialId = outlayJoin.materialId;
        this.ownerId = outlayJoin.ownerId;
        this.price = outlayJoin.price;
        this.day = outlayJoin.day;
        this.year = outlayJoin.year;
        this.month = outlayJoin.month;
        this.description = outlayJoin.outlay_description;
    }

    public Outlay() {
    }
}
