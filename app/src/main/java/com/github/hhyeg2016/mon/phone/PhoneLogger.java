package com.github.hhyeg2016.mon.phone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.github.hhyeg2016.mon.data.PhoneData;

import java.util.ArrayList;
import java.util.Arrays;

public class PhoneLogger {
    public static String PHONE = "PHONE_PING";

    public static ArrayList<PhoneData> getPhoneLogs(Context context) {

        ArrayList<PhoneData> listPhoneData = new ArrayList<>();

        Log.i(PHONE, "RUNNING");
        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) !=
                    PackageManager.PERMISSION_GRANTED) {
                // NO PERMISSIONS
                // TODO. Handle permissions
                Log.i(PHONE, "Read call log not set");
                return listPhoneData;
            }
            Cursor c = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI, null, null, null, null
            );
            /*
            // Column name types
             [contactid, logtype, sim_id, sec_record, real_phone_number, presentation,
              remind_me_later_set, e164_number, call_out_duration, vvm_id, countryiso,
              dormant_set, photo_id, type, is_read, address, number, photoring_uri, cityid,
              m_content, sdn_alpha_id, subscription_component_name, name, normalized_number,
              sec_custom1, raw_contact_id, simnum, country_code, fname, formatted_number,
              numbertype, sec_custom2, sns_tid, duration, callplus, account_id, geocoded_location,
              transcription, lookup_uri, cdnip_number, sns_pkey, frequent, messageid,
              subscription_id, _id, bname, sns_receiver_count, sp_type, pinyin_name, cnap_name,
              features, voicemail_uri, new, sec_groupid, sec_custom3, date, data_usage,
              numberlabel, reject_flag, service_type, m_subject, spam_report, matched_number,
              account_name, lname]
            */

            int number = c.getColumnIndex(CallLog.Calls.NUMBER);
            int type = c.getColumnIndex(CallLog.Calls.TYPE);
            int date = c.getColumnIndex(CallLog.Calls.DATE);
            int duration = c.getColumnIndex(CallLog.Calls.DURATION);
            Log.i(PHONE, Arrays.toString(c.getColumnNames()));
            while (c.moveToNext()) {
                String phNum = c.getString(number);
                int callType = c.getInt(type);
                String callDate = c.getString(date);
                Long longCallDate = Long.valueOf(callDate).longValue();
                String callDuration = c.getString(duration);

                PhoneData phoneData = new PhoneData(longCallDate, phNum, callType, callDuration);
                listPhoneData.add(phoneData);
            }

            c.close();
        } catch (Exception e) {
            Log.i(PHONE, e.toString());
        }

        return listPhoneData;
    }
}
