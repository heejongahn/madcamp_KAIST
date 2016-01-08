package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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