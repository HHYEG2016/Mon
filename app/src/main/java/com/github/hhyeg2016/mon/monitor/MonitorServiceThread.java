package com.github.hhyeg2016.mon.monitor;

import android.app.usage.UsageStats;
import android.content.Context;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MonitorServiceThread extends Thread {
    private static Context context;

    private static int pingCount = 0;
    private static int REFRESH_RATE = 20000;

    public MonitorServiceThread() {}

    public MonitorServiceThread(Context context) {
        MonitorServiceThread.context = context;
    }


    public void run() {
        do {
            //logUStats();
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

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.MILLISECOND, -REFRESH_RATE);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        // For now I have two ways of getting information from the UsageStatsManager
        // I can get the total foreground time of an app
        // I can also get the opening / closing events of an app

        List<UsageStats> list = usm.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime, endTime);
        for(int i = 0; i < list.size(); i++) {
            Log.d(TAG, String.valueOf(list.get(i).getPackageName()) + " " + String.valueOf(list.get(i).getTotalTimeInForeground()));
            Log.d(TAG, String.valueOf(list.get(i).getLastTimeStamp() - list.get(i).getFirstTimeStamp()));
            Log.d(TAG, "");
        }

        UsageEvents events = usm.queryEvents(startTime, endTime);
        while (events.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);

            if (e != null){
                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp() + "\t" + e.getEventType());
            }
        }
    }
}
