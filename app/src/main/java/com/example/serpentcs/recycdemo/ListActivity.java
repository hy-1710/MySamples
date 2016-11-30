package com.example.serpentcs.recycdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    public static final String TAG = "ListActivity";
    //    @BindView(R.id.tv_delivery_method)
//    TextView tv_delivery_method;
    public static final String URL = "http://www.wowlaundry.in/web-service/get_globalinfo?os=android&user_id=7";
    @BindView(R.id.activity_list)
    ListView activity_list;
    HttpHandler handler;
    List<String> area_lists;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        handler = new HttpHandler();
        area_lists = new ArrayList<>();
        adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, android.R.id.text1, area_lists);
        activity_list.setAdapter(adapter);
        new AreaValues().execute();
    }

    private class AreaValues extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String area;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ListActivity.this);
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String jsonString = handler.makeGetServicecall(URL);
            Log.e(TAG, "doInBackground:  Response from URL" + jsonString);
            if (jsonString != null && !jsonString.isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(jsonString);
                    JSONObject data = obj.getJSONObject("data");
                    JSONArray area_list = data.getJSONArray("area_list");
                    for (int i = 0; i < area_list.length(); i++) {
                        JSONObject o = area_list.getJSONObject(i);
                        area = o.getString("name");
                        area_lists.add(area);
                        Log.e(TAG, "doInBackground: area----->" + area);

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "doInBackground: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "JSON parsing Error : ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "doInBackground: Could not get JSON from Server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Could not get JSON from server", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing())
                dialog.dismiss();
            adapter.notifyDataSetChanged();

        }
    }
}
