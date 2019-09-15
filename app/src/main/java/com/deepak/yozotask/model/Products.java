package com.deepak.yozotask.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Products {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "product_name")
    private String product_name;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "image")
    private String image;

    public Products(int id, String product_name, String price, String company, String image) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.company = company;
        this.image = image;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}

