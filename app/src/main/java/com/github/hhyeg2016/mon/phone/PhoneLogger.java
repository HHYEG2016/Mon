package com.github.hhyeg2016.mon.phone;

import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;

public class PhoneLogger {
    PhoneLogger() {

    }

    Uri allCalls = Uri.parse("content://call_log/calls");
    //Cursor c = managedQuery(allCalls, null, null, null, null);

    //String num = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));// for  number
    //String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
    //String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));// for duration
    //int type = Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going.

}
