package com.example.serpentcs.recycdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serpentcs.recycdemo.model.TaskModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity extends AppCompatActivity {

    public static final String TAG = "TaskActivity";
    public static final String URL = "http://www.wowlaundry.in/web-service/get_globalinfo?os=android&user_id=7";
    @BindView(R.id.name)
    TextView tv_name;
    @BindView(R.id.email)
    TextView tv_email;
    @BindView(R.id.add)
    TextView tv_add;
    @BindView(R.id.frnd_order)
    TextView frnd_order;
    @BindView(R.id.min_order)
    TextView min_order;
    @BindView(R.id.btn_area_list)
    Button btn_area_list;
    @BindView(R.id.btn_delivery_method)
    Button btn_delivery_method;
    HttpHandler handler;
    ProgressDialog dialog;
    private List<TaskModel> modelList;
    private TaskModel taskModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        handler = new HttpHandler();
        taskModel = new TaskModel();

        btn_area_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskActivity.this, RecyclerActivity.class);
                startActivity(i);
            }
        });

        btn_delivery_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TaskActivity.this, ListActivity.class);
                startActivity(i);
            }
        });
        new Getvalues().execute();
    }

    private class Getvalues extends AsyncTask<Void, Void, Void> {

        String refer_friend_order;
        String email;
        String name;
        String add;
        String minimum_order;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TaskActivity.this);
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
                    JSONArray order_array = data.getJSONArray("refer_friend_order");
                    for (int i = 0; i < order_array.length(); i++) {
                        refer_friend_order = order_array.getString(i);
                        Log.e(TAG, "doInBackground:  refer_friend_order-->  " + refer_friend_order);

                    }
                    JSONArray user_info = data.getJSONArray("user_info");
                    Log.e(TAG, "doInBackground: user_info" + user_info);
                    for (int i = 0; i < user_info.length(); i++) {
                        JSONObject o = user_info.getJSONObject(i);
                        email = o.getString("email");
                        name = o.getString("display_name");
                        add = o.getString("address");
                        Log.e(TAG, "doInBackground: email---> " + email);
                        Log.e(TAG, "doInBackground: name---> " + name);
                        Log.e(TAG, "doInBackground: add---> " + add);
                    }
                    minimum_order = data.getString("minimum_order_amt");
                    Log.e(TAG, "doInBackground: minimum_order------>" + minimum_order);

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
            if (dialog.isShowing()) ;
            dialog.dismiss();
            frnd_order.setText(refer_friend_order);
            tv_name.setText(name);
            tv_email.setText(email);
            tv_add.setText(add);
            min_order.setText(minimum_order);
        }


    }
}
