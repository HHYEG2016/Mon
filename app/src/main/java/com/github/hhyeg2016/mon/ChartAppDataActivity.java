package com.github.hhyeg2016.mon;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.hhyeg2016.mon.data.AppData;
import com.github.hhyeg2016.mon.data_logger.AppLogger;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChartAppDataActivity extends AppCompatActivity {

    private BarChart mChart;
    private static int appNameSelectorIndex = 0;
    private static int appDateSelectorIndex = 0;
    private static String[] names;
    private static String[] dates;
    private static ArrayList<AppData> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdata_barchart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mChart = (BarChart) findViewById(R.id.chart1);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        XAxis botAxis = mChart.getXAxis();
        botAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //botAxis.setAxisMaxValue(10f);
        //botAxis.setAxisMinValue(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.setAxisMaxValue(10f);
        //leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);
        mData = AppLogger.getAppDataLogs(getApplicationContext());
        initializeData();
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.graph_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleHighlightArrow: {
                if (mChart.isDrawHighlightArrowEnabled())
                    mChart.setDrawHighlightArrow(false);
                else
                    mChart.setDrawHighlightArrow(true);
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }
            case R.id.actionSave: {
                if (mChart.saveToGallery("title" + System.currentTimeMillis(), 50)) {
                    Toast.makeText(getApplicationContext(), "Saving SUCCESSFUL!",
                            Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Saving FAILED!", Toast.LENGTH_SHORT)
                            .show();
                break;
            }
        }
        return true;
    }

    private void initializeData() {
        // Setting app name data
        Set<String> appNames = new HashSet<>();
        Set<String> appTimes = new HashSet<>();
        for (AppData appData : mData) {
            appNames.add(appData.getAppName());
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(appData.getLogTime());
            String date = "" + cl.get(Calendar.DAY_OF_MONTH) + " " + cl.get(Calendar.MONTH) + " " + cl.get(Calendar.YEAR);
            appTimes.add(date);
        }
        Spinner app_name_spinner = (Spinner) findViewById(R.id.app_name_spinner);
        names = appNames.toArray(new String[appNames.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                names
        );
        // Apply the adapter to the spinner
        app_name_spinner.setAdapter(adapter);
        Spinner app_date_spinner = (Spinner) findViewById(R.id.ad_date_spinner);
        dates = appTimes.toArray(new String[appTimes.size()]);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dates
        );
        app_date_spinner.setAdapter(adapterDate);

        app_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                appNameSelectorIndex = position;
                redraw_graph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        app_date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                appDateSelectorIndex = position;
                redraw_graph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private void redraw_graph() {
        String name = names[appNameSelectorIndex];
        String date = dates[appDateSelectorIndex];


        int numHours = 24;
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < numHours; i++) {
            xVals.add("hour: " + (i));
        }
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        HashMap<String, Integer> hmap = new HashMap<>();
        for (AppData mDataVal : mData) {
            if (mDataVal.getAppName().equals(name)) {
                Calendar cl = Calendar.getInstance();
                cl.setTimeInMillis(mDataVal.getLogTime());
                String compVal = "" + cl.get(Calendar.DAY_OF_MONTH) + " " + cl.get(Calendar.MONTH) + " " + cl.get(Calendar.YEAR);
                if(compVal.equals(date)) {

                    String dval = "" + cl.get(Calendar.HOUR_OF_DAY);
                    if (hmap.get(dval) == null) {
                        hmap.put(dval, 0);
                    } else {
                        int curCount = hmap.get(dval);
                        curCount++;
                        hmap.put(dval, curCount);
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : hmap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            yVals1.add(new BarEntry(Integer.valueOf(key), value));
        }


        // create a dataset and give it a type
        BarDataSet phSet = new BarDataSet(yVals1, "testVal");
        phSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        phSet.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(phSet);
        // create a data object with the datasets
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);

    }
}
