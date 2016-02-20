package com.github.hhyeg2016.mon.data;

/**
 * Created by Daniel on 2016-02-20.
 */
public class State {
    private static State ourInstance = new State();
    private DataCollection dataCollection;

    private State() {
    }

    public static State getInstance() {
        return ourInstance;
    }

    public DataCollection getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(DataCollection dataCollection) {
        this.dataCollection = dataCollection;
    }
}
