package com.example.serpentcs.recycdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gospelware.liquidbutton.LiquidButton;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LiquidButton liquidButton = (LiquidButton) findViewById(R.id.button);

        liquidButton.startPour();
        liquidButton.setFillAfter(true);
        liquidButton.setAutoPlay(true);
        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {
//                Toast.makeText(SplashActivity.this, "Finish", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onProgressUpdate(float progress) {
//                textView.setText(String.format("%.2f", progress * 100) + "%");
            }
        });
    }
}
