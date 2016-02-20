package com.github.hhyeg2016.mon.data;

/**
 * Created by Daniel on 2016-02-20.
 */
public class AppData extends Data {
    private Integer duration;
    private String content;

    public AppData(Integer logTime, Integer duration, String content) {
        this.setType("AppData");
        this.setLogTime(logTime);
        this.setDuration(duration);
        this.setContent(content);
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
