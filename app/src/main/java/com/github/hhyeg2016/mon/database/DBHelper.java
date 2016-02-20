package com.github.hhyeg2016.mon.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper dbObject = null;

    public static DBHelper getInstance(Context context) {
        if (dbObject == null) {
            dbObject = new DBHelper(context);
        }
        return dbObject;
    }

    protected DBHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null,
                DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.AppTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.AppTable.SQL_DELETE_TABLE);
        onCreate(db);
    }
}