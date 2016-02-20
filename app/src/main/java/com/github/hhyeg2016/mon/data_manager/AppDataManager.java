package com.github.hhyeg2016.mon.data_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.hhyeg2016.mon.data.AppData;
import com.github.hhyeg2016.mon.database.DBContract;
import com.github.hhyeg2016.mon.database.DBHelper;

import java.util.ArrayList;
import java.util.UUID;

public class AppDataManager  extends StoringManager<AppData>{
    private static DBHelper helper = null;
    protected ContentValues values;

    public AppDataManager(Context context) {
        helper = DBHelper.getInstance(context);
    }

    @Override
    public void insert(AppData object) {
        SQLiteDatabase db = helper.getWritableDatabase();
        setContentValues(object);
        db.insert(DBContract.AppTable.TABLE_NAME, null, values);
    }

    @Override
    public ArrayList<AppData> retrieveAll() {
        ArrayList<AppData> results = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        // Querying the database
        Cursor cursor = db.query(DBContract.AppTable.TABLE_NAME, null, null,
                null, null, null, null);

        // Retrieving all the entries
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AppData appData = new AppData(
                    cursor.getLong(1), // Log Time
                    cursor.getString(2), // App Name
                    cursor.getString(3) // Event Type
            );
            results.add(appData);
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    @Override
    public AppData getById(UUID id) {
        return null;
    }

    private void setContentValues(AppData appData) {
        values = new ContentValues();
        values.put(DBContract.AppTable.LOG_TIME, appData.getLogTime());
        values.put(DBContract.AppTable.APP_NAME, appData.getAppName());
        values.put(DBContract.AppTable.EVENT_TYPE, appData.getEventType());
    }

}