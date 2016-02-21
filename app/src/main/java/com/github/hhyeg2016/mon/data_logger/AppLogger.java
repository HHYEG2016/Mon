package com.github.hhyeg2016.mon.data_logger;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import com.github.hhyeg2016.mon.data.AppData;
import com.github.hhyeg2016.mon.data_manager.AppDataManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Conner on 16-02-21.
 */
public class AppLogger {
    public static ArrayList<AppData> getAppDataLogs(Context context) {
        int TIME_TO_GO_BACK = 1; // in years
        ArrayList<AppData> appDataArrayList = new ArrayList<>();

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -TIME_TO_GO_BACK);
        long startTime = calendar.getTimeInMillis();

        UsageEvents events = usm.queryEvents(startTime, endTime);
        while (events.hasNextEvent()) {
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);

            AppData appData = new AppData(e.getTimeStamp(), e.getPackageName(), String.valueOf(e.getEventType()));
            appDataArrayList.add(appData);
        }

        return appDataArrayList;
    }
}
