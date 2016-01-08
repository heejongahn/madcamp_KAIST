package com.example.nobell.project3.dataset;

import android.database.sqlite.SQLiteDatabase;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */
public class FriendTable {
    private static final String FRIEND_TABLE = "tbl_friends";
    public static void createTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FRIEND_TABLE + " (" +
                ")");
    }
    public static void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + FRIEND_TABLE);
    }
}
