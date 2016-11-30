package com.example.serpentcs.recycdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    public static final String URL = "http://parthdhorda.co/coke/web/login.php";
    @BindView(R.id.tie_pwd)
    TextInputEditText pwd;
    @BindView(R.id.tie_username)
    TextInputEditText username;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.btn_login)
    Button login;
    String Username;
    String Password;
    HttpHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        handler = new HttpHandler();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = username.getText().toString();
                Password = pwd.getText().toString();
                fetchingValue();


            }
        });
    }

    public void fetchingValue() {
        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        String jsonUserName;
        String jsonName;
        String concateUrl;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            concateUrl = URL + "?username=" + Username + "&password=" + Password;
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Loading");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HashMap<String, String> param = new HashMap<>();
            param.put("username", Username);
            param.put("password", Password);
            Log.e(TAG, "doInBackground: " + param);
            String jsonString = handler.makePostServiceCall(URL, param);
            Log.e(TAG, "doInBackground: Response from JsonString" + jsonString);
            if (jsonString != null && !jsonString.isEmpty()) {
                try {
                    JSONObject obj = new JSONObject(jsonString);
                    message = obj.getString("message");
                    Log.e(TAG, "doInBackground: MESSAGE STATUS" + message);
                    ;
                    if (!message.toLowerCase().equals("incorrect")) {
                        JSONArray data = obj.getJSONArray("data");
                        Log.e(TAG, "doInBackground: JSONARRAY ..." + data);
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject o = data.getJSONObject(i);
                            jsonUserName = o.getString("Username");
                            jsonName = o.getString("Name");
                            Log.e(TAG, "doInBackground: JSONUSERNAME" + jsonUserName);
                            Log.e(TAG, "doInBackground: JSONNAME" + jsonName);
                        }

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "doInBackground: JSON Exception" + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Json Parsing Error: ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }

            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (dialog.isShowing())
                dialog.dismiss();
            if (message.toLowerCase().equals("incorrect")) {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            } else {
                tv_name.setText(jsonName);
                tv_username.setText(jsonUserName);

            }

        }
    }
}
