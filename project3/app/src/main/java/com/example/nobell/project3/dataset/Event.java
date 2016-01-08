package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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

    public Date getDate() {
        return new Date(this.date);
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
        return new Select("Friend")
                .from(Appearance.class)
                .where("Event = ?", this.getId())
                .execute();
    }

    public List<Tag> getTags() {
        return new Select("Tag")
                .from(Description.class)
                .where("Event = ?", this.getId())
                .execute();
    }
}