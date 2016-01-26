package com.example.nobell.project4;

public class Feed_item {
    Shop_item shop;
    String body;
    String date;
    String feedid;
    String image;

    Shop_item get_shop () {
        return shop;
    }

    String get_body () {
        return body;
    }

    String get_date () {
        return date;
    }

    String get_feedid () {
        return feedid;
    }

    String get_image () {return image;}


    Feed_item(Shop_item shop, String body, String date, String feedid, String image){
        this.shop = shop;
        this.body = body;
        this.date = date;
        this.feedid = feedid;
        if (image=="") {
            this.image = null;
        }
        else {
            this.image = image;
        }
    }
}