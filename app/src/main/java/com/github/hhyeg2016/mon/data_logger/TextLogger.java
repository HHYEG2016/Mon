package com.github.hhyeg2016.mon.data_logger;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.github.hhyeg2016.mon.data.TextData;

import java.util.ArrayList;

public class TextLogger {
    public static String TEXT = "TEXT_PING";

    private static ArrayList<TextData> getTextLogsPerURI(Uri uriSMSURI, Context context, String type) {
        ArrayList<TextData> tdList = new ArrayList<>();
        Cursor c = context.getContentResolver().query(uriSMSURI, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                int date_idx = c.getColumnIndex("date");
                int read_idx = c.getColumnIndex("read");
                int addr_idx = c.getColumnIndex("address");

                long date_val = -1;
                if (date_idx != -1) {
                    date_val = c.getLong(date_idx);
                }

                String state_val = "Unknown";
                if (read_idx != -1) {
                    if (c.getInt(read_idx) == 1) {
                        state_val = "Read";
                    } else if (c.getInt(read_idx) == 0) {
                        state_val = "Unread";
                    }
                }

                String addr_val = "Unknown";
                if (addr_idx != -1) {
                    addr_val = c.getString(addr_idx);
                }
                TextData td = new TextData(date_val, type, state_val, addr_val);
                tdList.add(td);
            }
            c.close();
        } else {
            Log.i(TEXT, "no col names");
        }
        return tdList;
    }

    public static ArrayList<TextData> getTextLogs(Context context) {
        ArrayList<TextData> tdList = new ArrayList<>();
        Log.i(TEXT, "RUNNING");
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) !=
                    PackageManager.PERMISSION_GRANTED) {
                // NO PERMISSIONS
                // TODO. Handle permissions
                Log.i(TEXT, "Read sms log not set");
                return tdList;
            }
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            tdList.addAll(getTextLogsPerURI(uriSMSURI, context, "Incoming"));
            uriSMSURI = Uri.parse("content://sms/sent");
            tdList.addAll(getTextLogsPerURI(uriSMSURI, context, "Outgoing"));
        } catch (Exception e) {
            Log.i(TEXT, e.toString());
        }
        return tdList;
    }
}
