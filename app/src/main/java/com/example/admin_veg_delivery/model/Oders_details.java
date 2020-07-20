package com.example.admin_veg_delivery.model;

public class Oders_details {
    String user_no;
    String user_add;
    String oder_time;
    String oders;

    public Oders_details(String user_no, String user_add, String oder_time, String oders) {
        this.user_no = user_no;
        this.user_add = user_add;
        this.oder_time = oder_time;
        this.oders = oders;
    }

    @Override
    public String toString() {
        return "Oders_details{" +
                "user_no='" + user_no + '\'' +
                ", user_add='" + user_add + '\'' +
                ", oder_time='" + oder_time + '\'' +
                ", oders='" + oders + '\'' +
                '}';
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getUser_add() {
        return user_add;
    }

    public void setUser_add(String user_add) {
        this.user_add = user_add;
    }

    public String getOder_time() {
        return oder_time;
    }

    public void setOder_time(String oder_time) {
        this.oder_time = oder_time;
    }

    public String getOders() {
        return oders;
    }

    public void setOders(String oders) {
        this.oders = oders;
    }
}
