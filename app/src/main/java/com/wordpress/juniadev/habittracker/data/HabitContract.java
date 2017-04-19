package com.wordpress.juniadev.habittracker.data;

import android.provider.BaseColumns;

/**
 * Contract class that defines name of table and constants for the HabitTracker app.
 */
public final class HabitContract {

    private HabitContract() {
        // Private constructor to prevent instantiation.
    }

    public static final class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_MINUTES_SPENT = "minutes_spent";
        public static final String COLUMN_DATETIME = "date_time";
    }
}
