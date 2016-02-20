package com.github.hhyeg2016.mon.database;

import android.provider.BaseColumns;

// http://developer.android.com/training/basics/data-storage/databases.html
public final class DBContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hhyeg2016Mon.db";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {
    }

    public static abstract class AppTable implements BaseColumns {

        private AppTable() {
        }

        public static final String TABLE_NAME = "AppTable";
        public static final String LOG_TIME = "LogTime";
        public static final String APP_NAME = "AppName";
        public static final String EVENT_TYPE = "EventType";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + AppTable.TABLE_NAME + " ("
                + AppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AppTable.LOG_TIME + " LONG, "
                + AppTable.APP_NAME + " TEXT, "
                + AppTable.EVENT_TYPE + " TEXT"
                + ")";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS "
                + AppTable.TABLE_NAME;
    }
}
