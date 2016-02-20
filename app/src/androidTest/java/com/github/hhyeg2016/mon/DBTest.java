package com.github.hhyeg2016.mon;

import android.test.ActivityInstrumentationTestCase2;

import com.github.hhyeg2016.mon.data.DataManager;

/**
 * Created by Daniel on 2016-02-20.
 */
public class DBTest extends ActivityInstrumentationTestCase2 {

    public DBTest() {
        super(MainActivity.class);
    }

    public void testSaveData() {
        DataManager dataManager = new DataManager(getActivity());

    }
}
