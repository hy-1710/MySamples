package com.example.serpentcs.recycdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.serpentcs.recycdemo.adapter.ServiceAdapter;
import com.example.serpentcs.recycdemo.model.SerModel;
import com.example.serpentcs.recycdemo.service.MyService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceActivity extends AppCompatActivity {
    public static final String TAG = "ServiceActivity";
    @BindView(R.id.area_listview)
    ListView area_listview;
    ServiceAdapter adapter;
    ArrayList<SerModel> list;
    BroadcastReceiver value = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d(TAG, "onReceive() called with: context = ["
                    + context + "], intent = [" + intent + "]");
            list = intent.getParcelableArrayListExtra("list");
            Log.e(TAG, "onReceive: " + list.size());
            adapter = new ServiceAdapter(ServiceActivity.this, list);
            area_listview.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        startService(new Intent(this, MyService.class));


    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(value);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(value, new IntentFilter("GetValue"));
    }

}
