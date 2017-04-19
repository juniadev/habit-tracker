package com.wordpress.juniadev.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DB Helper for HabitsTracker app.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "habits_tracker.db";

    private static final String SQL_CREATE_HABITS_TABLE =
            "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " (" +
                    HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HabitContract.HabitEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                    HabitContract.HabitEntry.COLUMN_MINUTES_SPENT + " INTEGER NOT NULL, " +
                    HabitContract.HabitEntry.COLUMN_DATETIME + " INTEGER NOT NULL);";

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("HabitDbHelper", "Creating table with SQL: " + SQL_CREATE_HABITS_TABLE);
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Database in version 1, nothing to do here.
    }
}
