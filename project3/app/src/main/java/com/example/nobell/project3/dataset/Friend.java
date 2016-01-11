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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */

@Table(name="Friends")
public class Friend extends Model {
    private static Map<Long, Friend> cache = new HashMap<Long, Friend>();

    @Column(name = "Name")
    public String name;

    @Column(name = "Photo")
    public byte[] photo ;

    @Column(name = "PhoneNumber")
    public String phoneNumber;

    @Column(name = "Memo")
    public String memo;

    public void setPhoto(Bitmap bitmap) {
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, blob);
        photo = blob.toByteArray();
    }

    public Bitmap getPhoto() {
        if (this.photo == null) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(this.photo, 0, this.photo.length);
        return bitmap;
    }

    public Friend() {
        super();
    }

    public Friend(String name, String phoneNumber, String memo) {
        super();
        this.name = name;
        this.photo = null;
        this.phoneNumber = phoneNumber;
        this.memo = memo;
    }

    public List<Event> getEvents() {

        List<Appearance> appearances = new Select()
                .from(Appearance.class)
                .where("Friend = ?", this.getId())
                .execute();

        List<Event> events = new ArrayList<Event>();
        for (Appearance appearance: appearances) {
            events.add(appearance.event);
        }

        return events;
    }

    public static Long getIdWithCache(Friend f) {
        Long id = f.getId();
        cache.put(id, f);
        return id;
    }
    public static Friend flushCache(Long id) {
        Friend f = cache.get(id);
        if (f == null) {
            throw new RuntimeException(Friend.class.getName()
                    + ": cache could not found in flushCache(id="+id+")");
        }
        cache.remove(id);
        return f;
    }
}