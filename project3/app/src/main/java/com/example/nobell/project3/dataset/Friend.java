package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */

@Table(name="Friends")
public class Friend extends Model {
    @Column(name = "Name")
    public String name;

    @Column(name = "Photo")
    public byte[] photo;

    public void setPhoto(Bitmap bitmap) {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
        photo = blob.toByteArray();
    }

    public Bitmap getPhoto() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        return bitmap;
    }

    public Friend() {
        super();
    }

    public Friend(String name) {
        super();
        this.name = name;
    }

    public List<Event> getEvents() {
        return new Select("Event")
                .from(Appearance.class)
                .where("Friend = ?", this.getId())
                .execute();
    }
}