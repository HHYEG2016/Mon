package com.github.hhyeg2016.mon;

import java.util.ArrayList;

/**
 * Created by Daniel on 2016-02-20.
 */
public class DataCollection {
    private ArrayList<Data> dataCollection;

    public DataCollection() {
    }

    public void addCall(Integer logTime, Integer callDuration) {
        Call call = new Call(logTime, callDuration);
        dataCollection.add(call);
    }

    public void addText(Integer logTime) {
        Text text = new Text(logTime);
        dataCollection.add(text);
    }

    public void addApp(Integer logTime, Integer duration) {
        App app = new App(logTime, duration);
        dataCollection.add(app);
    }

    public void addLocaiton(Integer logTime, Float x, Float y) {
        Location location = new Location(logTime, x, y);
        dataCollection.add(location);
    }
}
