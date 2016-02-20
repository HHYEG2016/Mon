package com.github.hhyeg2016.mon.monitor;

import android.content.Context;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MonitorServiceThread extends Thread {
    public static String SERVICE_THREAD_HASH = "SERVICE_THREAD_HASH";
    public static String SERVICE_THREAD_PING_COUNT = "SERVICE_THREAD_PING_COUNT";

    private static Context context;

    private static int pingCount = 0;
    private static int REFRESH_RATE = 1000;

    public MonitorServiceThread() {}

    public MonitorServiceThread(Context context) {
        MonitorServiceThread.context = context;
    }


    public void run() {
        do {
            Log.i(SERVICE_THREAD_HASH, "id: " + this.hashCode());
            Log.i(SERVICE_THREAD_PING_COUNT, "pc: " + pingCount);
            logUStats();
            pingCount++;
            try {
                Thread.sleep(REFRESH_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    public static void main(String args[]) {
        (new MonitorServiceThread()).start();
    }


    // Note user must enable permissions through "Settings > Security > Apps with usage access" first
    public static void logUStats(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
        String TAG = "logUStats";

        //noinspection ResourceType
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, -REFRESH_RATE);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime,endTime);
        while (uEvents.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null){
                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
            }
        }
    }
}
