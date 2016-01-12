package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.FileReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name="Events")
public class Event extends Model {
    private static Map<Long, Event> cache = new HashMap<Long, Event>();
    private static Comparator<Event> comparator;

    @Column(name = "Body")
    public String body;

    @Column(name = "Date")
    public Long date;

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public String getDate() {
        if (this.date == null) {
            return null;
        }
        Date date = new Date(this.date);
        DateFormat mediumFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return mediumFormat.format(date);
    }

    public Event() {
        super();
        init();
    }

    public Event(String body, Date date) {
        super();
        this.body = body;
        this.setDate(date);
        init();
    }

    /* Initialize comparator, used in sort() */
    public void init() {
        if (comparator == null){
            comparator = new Comparator<Event> () {
                @Override
                public int compare (Event e1, Event e2) {
                    if (e1 == null)
                        return -1;
                    if (e2 == null)
                        return 1;
                    long dif = e1.date - e2.date;
                    return (dif > 0)? 1: ((dif < 0)? -1 : 0);
                }
            };
        }
    }
    /* Sort the given list. */
    public static void sort(List<Event> list, boolean inverse) {
        Collections.sort(list, comparator);
        if (inverse)
            Collections.reverse(list);
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

    public static Long getIdWithCache(Event e) {
        Long id = e.getId();
        cache.put(id, e);
        return id;
    }
    public static Event flushCache(Long id) {
        Event e = cache.get(id);
        if (e == null) {
            throw new RuntimeException(Event.class.getName()
                    + ": cache could not found in flushCache(id="+id+")");
        }
        cache.remove(id);
        return e;
    }
}