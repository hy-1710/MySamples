package com.example.serpentcs.recycdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.serpentcs.recycdemo.HttpHandler;
import com.example.serpentcs.recycdemo.model.SerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by serpentcs on 29/11/16.
 */

public class MyService extends Service {

    public static final String TAG = "MyService";
    public static final String URL = "http://www.wowlaundry.in/web-service/get_globalinfo?os=android&user_id=7";
    HttpHandler handler;
    ArrayList<SerModel> list;
    SerModel model;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet Implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new HttpHandler();
        list = new ArrayList<>();

        new GetValues().execute();
    }

    private class GetValues extends AsyncTask<Void, Void, Void> {
        String jsonString;
        String jsonName;
        String jsonId;


        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = handler.makeGetServicecall(URL);
            Log.e(TAG, "doInBackground: " + jsonString);
            if (jsonString != null && !jsonString.isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(jsonString);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray area_array = data.getJSONArray("area_list");
                    for (int i = 0; i < area_array.length(); i++) {
                        JSONObject o = area_array.getJSONObject(i);
                        jsonName = o.getString("name");
                        Log.e(TAG, "doInBackground: " + jsonName);
                        jsonId = o.getString("id");
                        Log.e(TAG, "doInBackground: " + jsonId);

                        list.add(new SerModel(jsonName, jsonId));
                        Log.e(TAG, "doInBackground: Model entry" + list.size());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent();
            i.setAction("GetValue");
            i.putParcelableArrayListExtra("list", list);
            sendBroadcast(i);
        }
    }
}
