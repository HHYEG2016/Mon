package com.github.hhyeg2016.mon;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.hhyeg2016.mon.data.AppData;
import com.github.hhyeg2016.mon.data_manager.AppDataManager;
import com.github.hhyeg2016.mon.display.DisplayAdapter;
import com.github.hhyeg2016.mon.monitor.MonitorService;
import com.github.hhyeg2016.mon.monitor.MonitorServiceThread;
import com.github.hhyeg2016.mon.phone.PhoneLogger;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static MonitorService mService;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            mService = ((MonitorService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // kludging a context in like a bau5
        new MonitorServiceThread(getApplicationContext());
        bindService(
                new Intent(getApplicationContext(), MonitorService.class),
                mConnection, BIND_AUTO_CREATE
        );
        addNotification();

        // CHECK PERMISSIONS
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CALL_LOG}, 0);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.PACKAGE_USAGE_STATS}, 1);
        }


        // phone stuff
        String[] phoneStuff = PhoneLogger.getPhoneLogs(getApplicationContext());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DisplayAdapter(phoneStuff, getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();

        // app stuff
        AppDataManager appDataManager = new AppDataManager(getApplicationContext());
        ArrayList<AppData> appDataList = appDataManager.retrieveAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager m_notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        m_notificationManager.cancel(this.hashCode());
    }

    private void addNotification() {
        // create the notification
        Notification.Builder m_notificationBuilder = new Notification.Builder(this)
                .setContentTitle(getText(R.string.service_name))
                .setContentText(getResources().getText(R.string.service_status_monitor))
                .setSmallIcon(R.mipmap.ic_visibility)
                .setOngoing(true);
        Notification notification = m_notificationBuilder.build();

        // create the pending intent and add to the notification
        Intent intent = new Intent(this, MonitorService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        m_notificationBuilder.setContentIntent(pendingIntent);

        // send the notification
        NotificationManager m_notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        m_notificationManager.notify(this.hashCode(), notification);
    }
}
