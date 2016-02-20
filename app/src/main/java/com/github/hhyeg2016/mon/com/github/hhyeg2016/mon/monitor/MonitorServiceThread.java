package com.github.hhyeg2016.mon.com.github.hhyeg2016.mon.monitor;

import android.util.Log;

public class MonitorServiceThread extends Thread {
    public static String SERVICE_THREAD_HASH = "SERVICE_THREAD_HASH";

    public void run() {
        while(true) {
            Log.i(SERVICE_THREAD_HASH, "id: " + this.hashCode());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        (new MonitorServiceThread()).start();
    }
}
