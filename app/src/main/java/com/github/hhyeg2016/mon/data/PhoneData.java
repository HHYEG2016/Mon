package com.github.hhyeg2016.mon.data;

import android.provider.CallLog;

import java.util.Date;

public class PhoneData extends Data {
    private String phoneNumber;
    private String callType;
    private String callDuration;

    public PhoneData(Long logTime, String phoneNumber, int cType, String cDuration) {
        this.setType("Phone");
        this.setLogTime(logTime);
        this.phoneNumber = phoneNumber;
        this.callType = "Unknown";
        switch (cType) {
            case CallLog.Calls.OUTGOING_TYPE:
               callType = "Outgoing";
                break;
            case CallLog.Calls.INCOMING_TYPE:
                callType = "Incoming";
                break;
            case CallLog.Calls.MISSED_TYPE:
                callType = "Missed";
                break;
        }
        this.callDuration = cDuration;
    }
}
