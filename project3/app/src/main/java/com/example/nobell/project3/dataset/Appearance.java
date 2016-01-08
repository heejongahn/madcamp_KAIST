package com.example.nobell.project3.dataset;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

@Table(name="Appearances")
public class Appearance extends Model {
    @Column(name = "Friend", onDelete = Column.ForeignKeyAction.CASCADE)
    public Friend friend;

    @Column(name = "Event", onDelete = Column.ForeignKeyAction.CASCADE)
    public Event event;

    public Appearance() {
        super();
    }

    public Appearance(Friend friend, Event event) {
        super();
        this.friend = friend;
        this.event = event;
    }
}