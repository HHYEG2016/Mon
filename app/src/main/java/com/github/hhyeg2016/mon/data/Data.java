package com.github.hhyeg2016.mon.data;

/**
 * Created by Daniel on 2016-02-20.
 */
public abstract class Data {
    private String type;

    public Integer getLogTime() {
        return logTime;
    }

    public void setLogTime(Integer logTime) {
        this.logTime = logTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Integer logTime;
}
