package com.example.admin_veg_delivery.model;

public class Product_details {
    Integer id;
    String imageurl;
    String name;
    String price;
    String price1;
    String kilogram;
    String kilogram1;


    public Product_details(Integer id, String imageurl, String name, String price, String price1, String kilogram, String kilogram1) {
        this.id=id;
        this.imageurl = imageurl;
        this.name = name;
        this.price = price;
        this.price1 = price1;
        this.kilogram = kilogram;
        this.kilogram1 = kilogram1;
    }

    @Override
    public String toString() {
        return "Product_details{" +
                "id=" + id +
                ", imageurl='" + imageurl + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", price1='" + price1 + '\'' +
                ", kilogram='" + kilogram + '\'' +
                ", kilogram1='" + kilogram1 + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getKilogram() {
        return kilogram;
    }

    public void setKilogram(String kilogram) {
        this.kilogram = kilogram;
    }

    public String getKilogram1() {
        return kilogram1;
    }

    public void setKilogram1(String kilogram1) {
        this.kilogram1 = kilogram1;
    }
}

