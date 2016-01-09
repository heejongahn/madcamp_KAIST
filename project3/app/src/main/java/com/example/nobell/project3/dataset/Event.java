package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.FileReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name="Events")
public class Event extends Model {
    @Column(name = "Body")
    public String body;

    @Column(name = "Date")
    public Long date;

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public String getDate() {
        Date date = new Date(this.date);
        DateFormat mediumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return mediumFormat.format(date);
    }

    public Event() {
        super();
    }

    public Event(String body, Date date) {
        super();
        this.body = body;
        this.setDate(date);
    }

    public void addFriend(Friend friend) {
        Appearance appearance = new Appearance(friend, this);
        appearance.save();
    }

    public void addTag(Tag tag) {
        Description description = new Description(tag, this);
        description.save();
    }

    public List<Friend> getFriends() {
        List<Appearance> appearances = new Select()
                .from(Appearance.class)
                .where("Event = ?", this.getId())
                .execute();

        List<Friend> friends = new ArrayList<Friend>();
        for (Appearance appearance: appearances) {
            friends.add(appearance.friend);
        }

        return friends;
    }

    public List<Tag> getTags() {
        List<Description> descriptions = new Select()
                .from(Description.class)
                .where("Event = ?", this.getId())
                .execute();

        List<Tag> tags = new ArrayList<Tag>();
        for (Description description: descriptions) {
            tags.add(description.tag);
        }

        return tags;
    }
}