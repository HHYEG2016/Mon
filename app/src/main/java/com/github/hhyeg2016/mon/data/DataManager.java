package com.github.hhyeg2016.mon.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.hhyeg2016.mon.database.DBContract;
import com.github.hhyeg2016.mon.database.DBHelper;

/**
 * Created by Daniel on 2016-02-20.
 */
public class DataManager {
    private static DBHelper helper = null;
    protected ContentValues values;

    public DataManager(Context context) {
        helper = DBHelper.getInstance(context);
    }

    public void insertAppTable(AppData app) {
        SQLiteDatabase db = helper.getWritableDatabase();
        values = new ContentValues();
        values.put(DBContract.AppTable.TIMESTAMP, app.getDuration());
        db.insert(DBContract.AppTable.TABLE_NAME, null, values);
    }
}
