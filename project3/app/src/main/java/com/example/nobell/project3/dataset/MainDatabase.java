package com.example.nobell.project3.dataset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nobell.project3.MainActivity;

/*
 * Reference:
 * https://github.com/wordpress-mobile/WordPress-Android
 */
public class MainDatabase extends SQLiteOpenHelper{
    protected static final String DB_NAME = "main.db";
    private static final int DB_VERSION = 1;

    /* database singleton */
    private static MainDatabase mDb;
    private final static Object mDbLock = new Object();
    public static MainDatabase getDatabase() {
        if (mDb == null) {
            synchronized (mDbLock) {
                if (mDb == null) {
                    mDb = new MainDatabase(MainActivity.getContext());  /* It might be changed into some Application class */
                    mDb.getWritableDatabase();
                }
            }
        }
        return mDb;
    }
    public static SQLiteDatabase getReadableDb() {
        return getDatabase().getReadableDatabase();
    }
    public static SQLiteDatabase getWritableDb() {
        return getDatabase().getWritableDatabase();
    }

    public MainDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // for now just reset the db when upgrading, future versions may want to avoid this
        // and modify table structures, etc., on upgrade while preserving data
        Log.i("MainDatabase", "Upgrading database from version " + oldVersion + " to version " + newVersion);
        reset(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // IMPORTANT: do NOT call super() here - doing so throws a SQLiteException
        Log.w("MainDatabase", "Downgrading database from version " + oldVersion + " to version " + newVersion);
        reset(db);
    }

    private void createAllTables(SQLiteDatabase db) {
        EventTable.createTables(db);
        FriendTable.createTables(db);
        TagTable.createTables(db);
    }

    private void dropAllTables(SQLiteDatabase db) {
        EventTable.dropTables(db);
        FriendTable.dropTables(db);
        TagTable.dropTables(db);
    }

    /*
     * drop & recreate all tables (essentially clears the db of all data)
     */
    private void reset(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            dropAllTables(db);
            createAllTables(db);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
}
