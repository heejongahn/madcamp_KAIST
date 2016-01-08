package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */

@Table(name="Tags")
public class Tag extends Model {
    @Column(name = "name")
    public String tagName;

    public Tag() {
        super();
    }

    public Tag(String name) {
        super();
        this.tagName = tagName;
    }

    public List<Event> getEvents() {
        return new Select("Event")
                .from(Description.class)
                .where("Tag = ?", this.getId())
                .execute();
    }
}