package com.github.hhyeg2016.mon;

/**
 * Created by Daniel on 2016-02-20.
 */
public class Location extends Data {
    private Float x;
    private Float y;

    public Location(Integer logTime, Float x, Float y) {
        this.setType("Location");
        this.setLogTime(logTime);
        this.setX(x);
        this.setY(y);
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }
}
