package com.example.serpentcs.recycdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.serpentcs.recycdemo.adapter.AreAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerActivity extends AppCompatActivity {
    //
//    @BindView(R.id.tv_area_list)
//    TextView tv_area_list;
    public static final String TAG = "RecyclerActivity";
    public static final String URL = "http://www.wowlaundry.in/web-service/get_globalinfo?os=android&user_id=7";
    @BindView(R.id.activity_recycler)
    RecyclerView activity_recycler;
    AreAdapter adapter;
    HttpHandler handler;
    List<String> del_meth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);
        handler = new HttpHandler();
        del_meth = new ArrayList<>();
        adapter = new AreAdapter(this, del_meth);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activity_recycler.setLayoutManager(manager);
        activity_recycler.setAdapter(adapter);
        activity_recycler.setItemAnimator(new DefaultItemAnimator());

        new DeliveryValues().execute();
    }


    private class DeliveryValues extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String delivery_method;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(RecyclerActivity.this);
            dialog.setMessage("Loading");
            dialog.setCancelable(true);
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
                    JSONArray del_method = data.getJSONArray("delivery_method");
                    for (int i = 0; i < del_method.length(); i++) {
                        JSONObject o = del_method.getJSONObject(i);
                        delivery_method = o.getString("delivery_type");
                        Log.e(TAG, "doInBackground: delivery_method----->" + delivery_method);
                        del_meth.add(delivery_method);
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
