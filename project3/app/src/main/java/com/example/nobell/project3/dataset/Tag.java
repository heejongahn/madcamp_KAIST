package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */

@Table(name="Tags")
public class Tag extends Model {
    private static Map<Long, Tag> cache = new HashMap<Long, Tag>();

    @Column(name = "name")
    public String tagName;

    public Tag() {
        super();
    }

    public Tag(String name) {
        super();
        this.tagName = name;
    }

    public List<Event> getEvents() {
        return new Select("Event")
                .from(Description.class)
                .where("Tag = ?", this.getId())
                .execute();
    }
    public List<Event> getEventsWithOrder() {
//        return new Select("Event")
//                .from(Event.class)
//                .innerJoin(Description.class)
//                .on("Events.id = Descriptions.Event")
//                .where("Tag = ?", this.getId())
//                //.orderBy("Date")
//                .execute(); // error
        List<Description> descriptions = new Select()
                .from(Description.class)
                .where("Tag = ?", this.getId())
                .execute();
        List<Event> events = new ArrayList<Event>();
        for (Description d:descriptions) {
            events.add(d.event);
        }
        Collections.sort(events, new Comparator<Event> () {
            @Override
            public int compare (Event e1, Event e2) {
                return (int)(e1.date - e2.date);
            }
        });
        return events;
    }

    public List<Friend> getFriendsTopThree() {
        return new Select("Friend")
                .from(Appearance.class)
                .innerJoin(Description.class)
                .on("Appearances.Event = Descriptions.Event")
                .where("Tag = ?", this.getId())
                .groupBy("Friend")
                .orderBy("COUNT(Friend)")
                .execute();
    }

    public static Tag addOrGet(String name) {
        Tag result = new Select().from(Tag.class).where("name = ?", name).executeSingle();
        if (result == null) {
            result = new Tag(name);
            result.save();
        }
        return result;
    }

    public static Long getIdWithCache(Tag t) {
        Long id = t.getId();
        cache.put(id, t);
        return id;
    }
    public static Tag flushCache(Long id) {
        Tag t = cache.get(id);
        if (t == null) {
            throw new RuntimeException(Tag.class.getName()
                    + ": cache could not found in flushCache(id="+id+")");
        }
        cache.remove(id);
        return t;
    }
}