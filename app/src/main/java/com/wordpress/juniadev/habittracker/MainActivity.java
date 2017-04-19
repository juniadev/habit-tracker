package com.wordpress.juniadev.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wordpress.juniadev.habittracker.data.HabitContract;
import com.wordpress.juniadev.habittracker.data.HabitDbHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String[] RANDOM_HABITS = {
            "Play piano",
            "Walk the dog",
            "Study Android development",
            "Read a book",
            "Cook dinner",
            "Go to the gym",
            "Take a walk",
            "Learn a new song",
            "Watch a movie"
    };

    private static final int MAX_MINUTES = 60;

    private HabitDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HabitDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        Cursor cursor = read();

        int idIdx = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
        int descriptionIdx = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DESCRIPTION);
        int minutesSpentIdx = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_MINUTES_SPENT);
        int datetimeIdx = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DATETIME);

        try {
            TextView textView = (TextView) findViewById(R.id.text_view_habit);
            textView.setText(cursor.getCount() + " records found in database!");

            while(cursor.moveToNext()) {
                textView.append("\n\nHabit ID: " + cursor.getInt(idIdx));
                textView.append("\nDescription: " + cursor.getString(descriptionIdx));
                textView.append("\nMinutes spent: " + cursor.getInt(minutesSpentIdx));
                textView.append("\nDate: " + getDateFormatted(cursor.getLong(datetimeIdx)));
            }
        } finally {
            cursor.close();
        }
    }

    private String getDateFormatted(final Long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return sdf.format(new Date(timeInMillis));
    }

    private Cursor read() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_DESCRIPTION,
                HabitContract.HabitEntry.COLUMN_MINUTES_SPENT,
                HabitContract.HabitEntry.COLUMN_DATETIME
        };

        return db.query(HabitContract.HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insert();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_DESCRIPTION, getRandomHabit());
        values.put(HabitContract.HabitEntry.COLUMN_MINUTES_SPENT, getRandomMinutes());
        values.put(HabitContract.HabitEntry.COLUMN_DATETIME, System.currentTimeMillis());

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        Log.i("MainActivity", "Inserted new habit with ID: " + newRowId);
    }

    private String getRandomHabit() {
        Random random = new Random();
        int randomInt = random.nextInt((RANDOM_HABITS.length));
        return RANDOM_HABITS[randomInt];
    }

    private int getRandomMinutes() {
        Random random = new Random();
        return random.nextInt(MAX_MINUTES);
    }
}
