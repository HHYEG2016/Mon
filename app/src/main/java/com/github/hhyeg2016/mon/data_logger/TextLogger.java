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
            Cursor c = context.getContentResolver().query(uriSMSURI, null, null, null, null);
            if (c != null) {
                /*
                String[] colNames;
                colNames = c.getColumnNames();
                // Log.i(TEXT, Arrays.toString(colNames));
                [_id, thread_id, address, person, date, date_sent, protocol, read, status, type,
                reply_path_present, subject, body, service_center, locked, error_code, sub_id,
                creator, seen, deletable, sim_slot, sim_imsi, hidden, group_id, group_type,
                delivery_date, app_id, msg_id, callback_number, reserved, pri, teleservice_id,
                link_url, svc_cmd, svc_cmd_content, roam_pending, spam_report, secret_mode,
                safe_message, favorite]
                 */
                /*
                while (c.moveToNext()) {
                    Log.i(TEXT, "SINGLE MSG START");
                    for (String colName : colNames) {
                        Log.i(TEXT, "\t" + colName + ": " + c.getString(c.getColumnIndex(colName)));
                    }
                }
                */
                while (c.moveToNext()) {
                    int date_idx = c.getColumnIndex("date");
                    int read_idx = c.getColumnIndex("read");
                    int addr_idx = c.getColumnIndex("address");

                    long date_val = -1;
                    if (date_idx != -1) {
                        date_val = c.getLong(date_idx);
                    }

                    String state_val = "unknown";
                    if (read_idx != -1) {
                        if (c.getInt(read_idx) == 1) {
                            state_val = "read";
                        } else if (c.getInt(read_idx) == 0) {
                            state_val = "unread";
                        }
                    }

                    String addr_val = "unknown";
                    if (addr_idx != -1) {
                        addr_val = c.getString(addr_idx);
                    }
                    TextData td = new TextData(date_val, "incomming", state_val, addr_val);
                    tdList.add(td);
                }
                c.close();
            } else {
                Log.i(TEXT, "no col names");
            }

        } catch (Exception e) {
            Log.i(TEXT, e.toString());
        }
        return tdList;
    }
}
