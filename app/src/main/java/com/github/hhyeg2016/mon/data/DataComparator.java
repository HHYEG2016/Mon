package com.github.hhyeg2016.mon.data;

import java.util.Comparator;

/**
 * Created by Daniel on 2016-02-20.
 */
public abstract class DataComparator implements Comparator<Data> {
    @Override
    public int compare(Data o1, Data o2) {
        return o2.getLogTime().compareTo(o1.getLogTime());
    }
}