package com.github.hhyeg2016.mon;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Daniel on 2016-02-20.
 */
public class DataManager {
    private String filePath = "bigBrother";
    private Gson gson = new Gson();
    private FileOutputStream outputStream;
    private Context context;

    public DataManager(Context context) {
        this.context = context;
    }

    public void saveDataCollection() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String userJson = gson.toJson(State.getInstance().getDataCollection());
                try {
                    outputStream = context.openFileOutput(filePath + ".data",
                            Context.MODE_PRIVATE);
                    outputStream.write(userJson.getBytes());
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        });

        thread.start();
    }

    public void loadDataCollection() {
        try {
            FileInputStream fis = context.openFileInput(filePath + ".data");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            Gson gson = new Gson();
            State.getInstance().setDataCollection(gson.fromJson(json, DataCollection.class));
        } catch (FileNotFoundException e) {
            State.getInstance().setDataCollection(new DataCollection());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
