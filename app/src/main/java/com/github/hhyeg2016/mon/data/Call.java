package com.github.hhyeg2016.mon.data;

/**
 * Created by Daniel on 2016-02-20.
 */
public class Call extends Data {
    private Integer callDuration;

    public Call(Integer logTime, Integer callDuration) {
        this.setType("Call");
        this.setLogTime(logTime);
        this.setCallDuration(callDuration);
    }

    public Integer getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(Integer callDuration) {
        this.callDuration = callDuration;
    }
}
