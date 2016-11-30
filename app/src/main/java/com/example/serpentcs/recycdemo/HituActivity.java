package com.example.serpentcs.recycdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HituActivity extends AppCompatActivity {

    public static final String TAG = "HituActivity";
    public static final String URL = "http://kookdokoo.com/test/service/feedback.php";
    String status;
    HttpHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitu);
        handler = new HttpHandler();
        new GetValues(this).execute();
    }

    private class GetValues extends AsyncTask<Object, Object, String> {
        ProgressDialog dialog;
        //         String user_id;
//         String subject;
//         String feedback;
//         String mem_id;
//         String noti_id;
//         FragmentActivity activity;
//         String jsonString;
        String user_id = "22";
        String subject = "9998285121";
        String feedback = "5545";
        String mem_id = "5";
        String noti_id = "25";
        FragmentActivity activity;
        String jsonString;


        public GetValues(FragmentActivity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(activity);
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Object... voids) {
            HashMap<String, String> map = new HashMap<>();
            map.put("user_id", user_id);
            map.put("subject", subject);
            map.put("feedback", feedback);
            map.put("mem_id", mem_id);
            map.put("noti_id", noti_id);

            try {
                jsonString = handler.postToServer(URL, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (jsonString != null && !jsonString.isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(jsonString);
                    Log.e(TAG, "doInBackground: " + obj);
                    status = obj.getString("status");
                    Log.e(TAG, "doInBackground: " + status);
                    return status;
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing())
                dialog.dismiss();
            if (jsonString != null) {
                Toast.makeText(activity, status, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Getting null value", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
