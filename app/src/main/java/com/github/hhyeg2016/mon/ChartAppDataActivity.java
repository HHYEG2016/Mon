package com.github.hhyeg2016.mon;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ChartAppDataActivity extends AppCompatActivity {

    private BarChart mChart;

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

        // add data
        // TODO: ArrayList<AppData> set
        setData(AppLogger.getAppDataLogs(getApplicationContext()));
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

    private void setData(ArrayList<AppData> adList) {
        int numHours = 24;
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < numHours; i++) {
            xVals.add((i) + "");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < numHours; i++) {

            Random r = new Random();
            int Low = 5;
            int High = 9;
            int Result = r.nextInt(High - Low) + Low;

            float val = (float) (Result);// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals1.add(new BarEntry(val, i));
        }

        // create a dataset and give it a type
        BarDataSet phSet = new BarDataSet(yVals1, "testVal");
        phSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        phSet.setColor(ColorTemplate.getHoloBlue());
        phSet.setHighLightColor(Color.rgb(244, 117, 117));
        phSet.setColors(ColorTemplate.JOYFUL_COLORS);
        //set1.setFillFormatter(new MyFillFormatter(0f));
//        set1.setDrawHorizontalHighlightIndicator(false);
//        set1.setVisible(false);
//        set1.setCircleHoleColor(Color.WHITE);


        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(phSet); // add the datasets
        // dataSets.add(phSet); // can have multiple data sets

        // create a data object with the datasets
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(9f);
//
//        // create a data object with the datasets
//        LineData data = new LineData(xVals, dataSets);
//
//        // set data
//        mChart.setData(data);


        // set data
        mChart.setData(data);


        // Setting app name data
        Set<String> appNames = new HashSet<>();
        Set<String> appTimes = new HashSet<>();
        for (AppData appData : adList) {
            appNames.add(appData.getAppName());
            Calendar cl = Calendar.getInstance();
            cl.setTimeInMillis(appData.getLogTime());
            String date = "" + cl.get(Calendar.DAY_OF_MONTH) + " " + cl.get(Calendar.MONTH) + " " + cl.get(Calendar.YEAR);
            appTimes.add(date);
        }
        Spinner app_name_spinner = (Spinner) findViewById(R.id.app_name_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                appNames.toArray(new String[appNames.size()])
        );
        // Apply the adapter to the spinner
        app_name_spinner.setAdapter(adapter);
        Spinner app_date_spinner = (Spinner) findViewById(R.id.ad_date_spinner);
        ArrayAdapter<String> adapterDate = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                appTimes.toArray(new String[appTimes.size()])
        );
        app_date_spinner.setAdapter(adapterDate);
    }
}
