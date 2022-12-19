package com.afaqsdk.demo;

import static com.afaqsdk.ads.util.Constant.ADMOB;
import static com.afaqsdk.ads.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;

import com.solodroid.ads.sdkdemo.R;

public class ActivitySplash extends AppCompatActivity {

    private static final long COUNTER_TIME = 2000;
    long secondsRemaining;
    Application application;
    AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        adManager = new AdManager(this);
        adManager.initializeAd();


        if (Constant.AD_NETWORK.equals(ADMOB)) {
            application = getApplication();
            ((MyApplication) application).showAdIfAvailable(ActivitySplash.this, this::createTimer);
        }

        else if (Constant.AD_NETWORK.equals(GOOGLE_AD_MANAGER)) {
            application = getApplication();
            ((MyApplication) application).showAdIfAvailable(ActivitySplash.this, this::createTimer);
        }
        else {
            startMainActivity();
        }

    }

    private void createTimer() {

        CountDownTimer countDownTimer = new CountDownTimer(COUNTER_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsRemaining = ((millisUntilFinished / 1000) + 1);
            }

            @Override
            public void onFinish() {
                secondsRemaining = 0;
                startMainActivity();
            }
        };
        countDownTimer.start();
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }

}
