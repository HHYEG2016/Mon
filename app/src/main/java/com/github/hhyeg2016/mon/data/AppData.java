package com.github.hhyeg2016.mon.data;

public class AppData extends Data {
    private String appName;
    private String eventType;


    public AppData(Long logTime, String appName, String eventType) {
        this.setType("AppData");
        this.setLogTime(logTime);
        this.setAppName(appName);
        this.setEventType(eventType);
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
