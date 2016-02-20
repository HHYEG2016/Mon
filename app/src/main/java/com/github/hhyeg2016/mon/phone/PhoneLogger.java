package com.github.hhyeg2016.mon.phone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Date;

public class PhoneLogger {
    public static void getPhoneLogs(Context context) {
        try {
            Cursor c = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI, null, null, null, null
            );
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) !=
                    PackageManager.PERMISSION_GRANTED) {
                // NO PERMISSIONS
                // TODO. Handle permissions
                return;
            }
            int number = c.getColumnIndex(CallLog.Calls.NUMBER);
            int type = c.getColumnIndex(CallLog.Calls.TYPE);
            int date = c.getColumnIndex(CallLog.Calls.DATE);
            int duration = c.getColumnIndex(CallLog.Calls.DURATION);

            while (c.moveToNext()) {
                String phNum = c.getString(number);
                String callType = c.getString(type);
                String callDate = c.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = c.getString(duration);
                Log.i("PHONE", phNum + " " + callType + " " + callDate + " " + callDuration);
            }

            c.close();
        } catch (Exception e) {
            Log.i("getPhoneLogs", e.toString());
        }
    }
}
