package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */
public class EventTable {
    private static final String EVENT_TABLE = "tbl_events";
    public static void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + EVENT_TABLE + " (" +
                   ")");
    }
    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
    }
}
