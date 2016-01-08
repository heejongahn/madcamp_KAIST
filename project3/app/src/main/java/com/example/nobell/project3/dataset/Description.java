package com.example.nobell.project3.dataset;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="Descriptions")
public class Description extends Model {
    @Column(name = "Tag", onDelete = Column.ForeignKeyAction.CASCADE)
    public Tag tag;

    @Column(name = "Event", onDelete = Column.ForeignKeyAction.CASCADE)
    public Event event;

    public Description() {
        super();
    }

    public Description(Tag tag, Event event) {
        super();
        this.tag = tag;
        this.event = event;
    }
}