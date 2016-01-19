package com.example.nobell.project4;

public class Shop_item {
    int image;
    String title;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }

    Shop_item(int image, String title){
        this.image=image;
        this.title=title;
    }
}