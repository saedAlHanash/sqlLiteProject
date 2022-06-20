package com.example.sqlliteproject.DataBases.Models;

public class OutlayJoin {

    public int materialId;
    public int ownerId;
    public int id;
    public float price;
    public int day;
    public int month;
    public int year;
    public String outlay_description;

    public String material_name;
    public String material_description;
    public boolean isService;

    public String owner_name;
    public String owner_description;

    @Override
    public String toString() {
        return "OutlayJoin{" +
                "materialId=" + materialId +
                ", ownerId=" + ownerId +
                ", id=" + id +
                ", price=" + price +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", outlay_description='" + outlay_description + '\'' +
                ", material_name='" + material_name + '\'' +
                ", material_description='" + material_description + '\'' +
                ", isService=" + isService +
                ", owner_name='" + owner_name + '\'' +
                ", owner_description='" + owner_description + '\'' +
                '}';
    }
}
