package com.github.hhyeg2016.mon.data;

/**
 * Created by Daniel on 2016-02-20.
 */
public class Text extends Data {
    private String message;

    public Text(Integer logTime, String message) {
        this.setType("Text");
        this.setLogTime(logTime);
        this.setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
