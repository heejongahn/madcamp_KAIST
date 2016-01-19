package com.example.nobell.project4;

public class Feed_item {
    int image;
    String title;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }

    Feed_item(int image, String title){
        this.image=image;
        this.title=title;
    }
}