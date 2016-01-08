package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */
public class TagTable {
    private static final String TAG_TABLE = "tbl_tags";
    public static void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TAG_TABLE + " (" +
                ")");
    }
    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TAG_TABLE);
    }
}
