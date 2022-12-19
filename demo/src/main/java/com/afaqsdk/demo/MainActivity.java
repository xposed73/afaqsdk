package com.afaqsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.solodroid.ads.sdkdemo.R;

public class MainActivity extends AppCompatActivity {

    AdManager adManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adManager = new AdManager(this);
        adManager.initializeAd();
        adManager.loadBannerAd(R.id.bannerAd);
        adManager.loadNativeAd(R.id.nativeAd);
        adManager.loadInterstitialAd(1, 1);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_interstitial).setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            adManager.showInterstitialAd();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}