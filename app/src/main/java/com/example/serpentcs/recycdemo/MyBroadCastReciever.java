package com.example.serpentcs.recycdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by serpentcs on 23/11/16.
 */

public class MyBroadCastReciever extends BroadcastReceiver {

    public static final String TAG = "MyBroadCastReciever";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: ");
        String i = intent.getAction();
        Log.e(TAG, "onReceive: " + i);
        if (i.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
            Toast.makeText(context, "charging", Toast.LENGTH_SHORT).show();
        }


    }
}
