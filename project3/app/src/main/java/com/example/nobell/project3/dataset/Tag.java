package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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