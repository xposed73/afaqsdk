package com.afaqsdk.demo;

import android.app.Activity;
import android.view.View;
import com.afaqsdk.ads.format.AdNetwork;
import com.afaqsdk.ads.format.BannerAd;
import com.afaqsdk.ads.format.InterstitialAd;
import com.afaqsdk.ads.format.NativeAd;
import com.afaqsdk.ads.format.NativeAdFragment;
import com.afaqsdk.ads.gdpr.GDPR;
import com.afaqsdk.ads.gdpr.LegacyGDPR;
import com.solodroid.ads.sdkdemo.BuildConfig;

public class AdManager {

    boolean LEGACY_GDPR = true;
    AdNetwork.Initialize adNetwork;
    BannerAd.Builder bannerAd;
    InterstitialAd.Builder interstitialAd;
    NativeAd.Builder nativeAd;
    NativeAdFragment.Builder nativeAdView;
    LegacyGDPR legacyGDPR;
    GDPR gdpr;
    Activity activity;

    public AdManager(Activity activity) {
        this.activity = activity;
        this.legacyGDPR = new LegacyGDPR(activity);
        this.gdpr = new GDPR(activity);
        adNetwork = new AdNetwork.Initialize(activity);
        bannerAd = new BannerAd.Builder(activity);
        interstitialAd = new InterstitialAd.Builder(activity);
        nativeAd = new NativeAd.Builder(activity);
        nativeAdView = new NativeAdFragment.Builder(activity);
    }

    public void initializeAd() {
        adNetwork.setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setDebug(BuildConfig.DEBUG)
                .build();
    }

    public void loadBannerAd(int placement) {
        bannerAd.setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobBannerId(Constant.ADMOB_BANNER_ID)
                .setGoogleAdManagerBannerId(Constant.GOOGLE_AD_MANAGER_BANNER_ID)
                .setFanBannerId(Constant.FAN_BANNER_ID)
                .setDarkTheme(false)
                .setPlacementStatus(placement)
                .setLegacyGDPR(LEGACY_GDPR)
                .build();
    }

    public void loadInterstitialAd(int placement, int interval) {
        interstitialAd.setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobInterstitialId(Constant.ADMOB_INTERSTITIAL_ID)
                .setGoogleAdManagerInterstitialId(Constant.GOOGLE_AD_MANAGER_INTERSTITIAL_ID)
                .setFanInterstitialId(Constant.FAN_INTERSTITIAL_ID)
                .setInterval(interval)
                .setPlacementStatus(placement)
                .setLegacyGDPR(LEGACY_GDPR)
                .build();
    }

    public void loadNativeAd(int placement) {
        nativeAd.setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobNativeId(Constant.ADMOB_NATIVE_ID)
                .setAdManagerNativeId(Constant.GOOGLE_AD_MANAGER_NATIVE_ID)
                .setFanNativeId(Constant.FAN_NATIVE_ID)
                .setDarkTheme(false)
                .setLegacyGDPR(LEGACY_GDPR)
                .build();
    }

    public void loadNativeAdView(View view, int placement) {
        nativeAdView.setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobNativeId(Constant.ADMOB_NATIVE_ID)
                .setAdManagerNativeId(Constant.GOOGLE_AD_MANAGER_NATIVE_ID)
                .setFanNativeId(Constant.FAN_NATIVE_ID)
                .setDarkTheme(false)
                .setLegacyGDPR(LEGACY_GDPR)
                .setView(view)
                .build();
    }

    public void showInterstitialAd() {
        interstitialAd.show();
    }

    public void updateConsentStatus() {
        if (LEGACY_GDPR) {
            legacyGDPR.updateLegacyGDPRConsentStatus(Constant.PUBLISHER_ID, Constant.PRIVACY_POLICY);
        } else {
            gdpr.updateGDPRConsentStatus();
        }
    }

}

