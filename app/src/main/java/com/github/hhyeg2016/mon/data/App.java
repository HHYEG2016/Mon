package com.github.hhyeg2016.mon.data;

/**
 * Created by Daniel on 2016-02-20.
 */
public class App extends Data {
    private Integer duration;

    public App(Integer logTime, Integer duration) {
        this.setType("App");
        this.setLogTime(logTime);
        this.setDuration(duration);
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
