package com.github.hhyeg2016.mon.database;

import android.provider.BaseColumns;

/**
 * Created by Daniel on 2016-02-20.
 */

// http://developer.android.com/training/basics/data-storage/databases.html
public final class DBContract {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bigBrother.db";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBContract() {
    }

    public static abstract class AppTable implements BaseColumns {

        private AppTable() {
        }

        public static final String TABLE_NAME = "AppTable";
        public static final String TIMESTAMP = "TimeStamp";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE "
                + AppTable.TABLE_NAME + " (" + AppTable._ID + " INTEGER,"
                + AppTable.TIMESTAMP + " TEXT"
                + ")";

        public static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS "
                + AppTable.TABLE_NAME;
    }
}