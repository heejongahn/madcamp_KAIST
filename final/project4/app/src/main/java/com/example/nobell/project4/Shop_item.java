package com.example.nobell.project4;

public class Shop_item {
    int image;
    String name, category, phone, location;

    int getImage(){
        return this.image;
    }
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

    Shop_item(int image, String name, String category, String phone, String location){
        this.image=image;
        this.name = name;
        this.category = category;
        this.phone = phone;
        this.location = location;
    }
}