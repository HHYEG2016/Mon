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

import com.github.hhyeg2016.mon.data.TextData;
import com.github.hhyeg2016.mon.data_logger.TextLogger;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChartTextDataActivity extends AppCompatActivity {
    private LineChart mChart;
    private static int appDateSelectorIndex = 0;
    private static String[] dates;
    private static ArrayList<TextData> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_text_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mChart = (LineChart) findViewById(R.id.chart1);

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
        mData = TextLogger.getTextLogs(getApplicationContext());
        Log.i("size of text log", String.valueOf(mData.size()));
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
        // Setting app time data
        Set<String> appTimes = new HashSet<>();
        for (TextData textData : mData) {
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(textData.getLogTime());
            String date = "" + cl.get(Calendar.DAY_OF_MONTH) + " " + cl.get(Calendar.MONTH) + " " + cl.get(Calendar.YEAR);
            appTimes.add(date);
        }
        Spinner app_date_spinner = (Spinner) findViewById(R.id.ad_date_spinner);
        dates = appTimes.toArray(new String[appTimes.size()]);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                dates
        );
        app_date_spinner.setAdapter(adapterDate);

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
        String date = dates[appDateSelectorIndex];


        int numHours = 24;
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < numHours; i++) {
            xVals.add("hour: " + (i));
        }
        ArrayList<Entry> yVals1 = new ArrayList<>();

        HashMap<String, Integer> hmap = new HashMap<>();
        for (TextData mDataVal : mData) {
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

        for (Map.Entry<String, Integer> entry : hmap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            yVals1.add(new Entry(Integer.valueOf(key), value));
        }


        // create a dataset and give it a type
        LineDataSet phSet = new LineDataSet(yVals1, "testVal");
        phSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        phSet.setColors(ColorTemplate.JOYFUL_COLORS);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(phSet);
        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);

    }
}
