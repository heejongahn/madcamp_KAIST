package com.example.nobell.project4;

public class Feed_item {
    Shop_item shop;
    String body;
    String date;


    Shop_item get_shop () {
        return shop;
    }

    String get_body () {
        return body;
    }

    String get_date () {
        return date;
    }

    Feed_item(Shop_item shop, String body, String date){
        this.shop = shop;
        this.body = body;
        this.date = date;
    }
}