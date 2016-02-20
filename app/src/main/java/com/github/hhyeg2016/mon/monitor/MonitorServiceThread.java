package com.github.hhyeg2016.mon.monitor;

import android.content.Context;

public class MonitorServiceThread extends Thread {
    private static Context context;

    private static int pingCount = 0;

    public MonitorServiceThread() {}

    public MonitorServiceThread(Context context) {
        MonitorServiceThread.context = context;
    }


    public void run() {
        do {
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
