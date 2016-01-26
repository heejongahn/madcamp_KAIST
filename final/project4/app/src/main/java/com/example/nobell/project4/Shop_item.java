package com.example.nobell.project4;

public class Shop_item {
    String image, name, category, phone, location, shopid;
    double latitude, longitude;

    String getImage(){ return this.image; }
    String getName(){
        return this.name;
    }
    String getCategory(){
        return this.category;
    }
    String getPhone(){
        return this.phone;
    }
    String getLocation(){
        return this.location;
    }
    double getLatitude() {return this.latitude;}
    double getLongitude() {return this.longitude;}
    String getShopid() {return this.shopid;}

    Shop_item(String image, String name, String category, String phone, String location, double latitude, double longitude, String shopid){
        this.image=image;
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shopid = shopid;
    }
}