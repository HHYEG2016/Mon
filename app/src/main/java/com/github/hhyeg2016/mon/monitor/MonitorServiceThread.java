package com.github.hhyeg2016.mon.monitor;

import android.content.Context;
import android.util.Log;

import com.github.hhyeg2016.mon.phone.PhoneLogger;

public class MonitorServiceThread extends Thread {
    public static String SERVICE_THREAD_HASH = "SERVICE_THREAD_HASH";
    public static String SERVICE_THREAD_PING_COUNT = "SERVICE_THREAD_PING_COUNT";

    private static Context context;

    private static int pingCount = 0;

    public MonitorServiceThread() {}

    public MonitorServiceThread(Context context) {
        MonitorServiceThread.context = context;
    }


    public void run() {
        do {
            Log.i(SERVICE_THREAD_HASH, "id: " + this.hashCode());
            Log.i(SERVICE_THREAD_PING_COUNT, "pc: " + pingCount);

            PhoneLogger.getPhoneLogs(MonitorServiceThread.context);

            pingCount++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static void main(String args[]) {
        (new MonitorServiceThread()).start();
    }
}
