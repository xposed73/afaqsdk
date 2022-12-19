package com.afaqsdk.ads.format;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.afaqsdk.R;
import com.afaqsdk.ads.util.Constant;
import com.afaqsdk.ads.util.Tools;
import com.facebook.ads.Ad;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdView;

public class BannerAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;
        private AdView adView;
        private AdManagerAdView adManagerAdView;
        private com.facebook.ads.AdView fanAdView;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobBannerId = "";
        private String googleAdManagerBannerId = "";
        private String fanBannerId = "";
        private int placementStatus = 1;
        private boolean darkTheme = false;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadBannerAd();
            return this;
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Builder setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Builder setAdMobBannerId(String adMobBannerId) {
            this.adMobBannerId = adMobBannerId;
            return this;
        }

        public Builder setGoogleAdManagerBannerId(String googleAdManagerBannerId) {
            this.googleAdManagerBannerId = googleAdManagerBannerId;
            return this;
        }

        public Builder setFanBannerId(String fanBannerId) {
            this.fanBannerId = fanBannerId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setDarkTheme(boolean darkTheme) {
            this.darkTheme = darkTheme;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadBannerAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case Constant.ADMOB:
                    case Constant.FAN_BIDDING_ADMOB:
                        FrameLayout adContainerView = activity.findViewById(R.id.admob_banner_view_container);
                        adContainerView.post(() -> {
                            adView = new AdView(activity);
                            adView.setAdUnitId(adMobBannerId);
                            adContainerView.removeAllViews();
                            adContainerView.addView(adView);
                            adView.setAdSize(Tools.getAdSize(activity));
                            adView.loadAd(Tools.getAdRequest(activity, legacyGDPR));
                            adView.setAdListener(new AdListener() {
                                @Override
                                public void onAdLoaded() {
                                    // Code to be executed when an ad finishes loading.
                                    adContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                    // Code to be executed when an ad request fails.
                                    adContainerView.setVisibility(View.GONE);
                                    loadBackupBannerAd();
                                }

                                @Override
                                public void onAdOpened() {
                                    // Code to be executed when an ad opens an overlay that
                                    // covers the screen.
                                }

                                @Override
                                public void onAdClicked() {
                                    // Code to be executed when the user clicks on an ad.
                                }

                                @Override
                                public void onAdClosed() {
                                    // Code to be executed when the user is about to return
                                    // to the app after tapping on an ad.
                                }
                            });
                        });
                        Log.d(TAG, adNetwork + " Banner Ad unit Id : " + adMobBannerId);
                        break;

                    case Constant.GOOGLE_AD_MANAGER:
                    case Constant.FAN_BIDDING_AD_MANAGER:
                        FrameLayout googleAdContainerView = activity.findViewById(R.id.google_ad_banner_view_container);
                        googleAdContainerView.post(() -> {
                            adManagerAdView = new AdManagerAdView(activity);
                            adManagerAdView.setAdUnitId(googleAdManagerBannerId);
                            googleAdContainerView.removeAllViews();
                            googleAdContainerView.addView(adManagerAdView);
                            adManagerAdView.setAdSize(Tools.getAdSize(activity));
                            adManagerAdView.loadAd(Tools.getGoogleAdManagerRequest());
                            adManagerAdView.setAdListener(new AdListener() {
                                @Override
                                public void onAdClicked() {
                                    super.onAdClicked();
                                }

                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    super.onAdFailedToLoad(loadAdError);
                                    googleAdContainerView.setVisibility(View.GONE);
                                    loadBackupBannerAd();
                                }

                                @Override
                                public void onAdImpression() {
                                    super.onAdImpression();
                                }

                                @Override
                                public void onAdLoaded() {
                                    super.onAdLoaded();
                                    googleAdContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdOpened() {
                                    super.onAdOpened();
                                }
                            });
                        });
                        break;

                    case Constant.FAN:
                        fanAdView = new com.facebook.ads.AdView(activity, fanBannerId, AdSize.BANNER_HEIGHT_50);
                        RelativeLayout fanAdViewContainer = activity.findViewById(R.id.fan_banner_view_container);
                        fanAdViewContainer.addView(fanAdView);
                        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                            @Override
                            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                                fanAdViewContainer.setVisibility(View.GONE);
                                loadBackupBannerAd();
                                Log.d(TAG, "Error load FAN : " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                fanAdViewContainer.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        };
                        com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = fanAdView.buildLoadAdConfig().withAdListener(adListener).build();
                        fanAdView.loadAd(loadAdConfig);
                        break;
                    case Constant.NONE:
                        //do nothing
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

        public void loadBackupBannerAd() {
            if (adStatus.equals(Constant.AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case Constant.ADMOB:
                    case Constant.FAN_BIDDING_ADMOB:
                        FrameLayout adContainerView = activity.findViewById(R.id.admob_banner_view_container);
                        adContainerView.post(() -> {
                            adView = new AdView(activity);
                            adView.setAdUnitId(adMobBannerId);
                            adContainerView.removeAllViews();
                            adContainerView.addView(adView);
                            adView.setAdSize(Tools.getAdSize(activity));
                            adView.loadAd(Tools.getAdRequest(activity, legacyGDPR));
                            adView.setAdListener(new AdListener() {
                                @Override
                                public void onAdLoaded() {
                                    // Code to be executed when an ad finishes loading.
                                    adContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                    // Code to be executed when an ad request fails.
                                    adContainerView.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAdOpened() {
                                    // Code to be executed when an ad opens an overlay that
                                    // covers the screen.
                                }

                                @Override
                                public void onAdClicked() {
                                    // Code to be executed when the user clicks on an ad.
                                }

                                @Override
                                public void onAdClosed() {
                                    // Code to be executed when the user is about to return
                                    // to the app after tapping on an ad.
                                }
                            });
                        });
                        Log.d(TAG, adNetwork + " Banner Ad unit Id : " + adMobBannerId);
                        break;

                    case Constant.GOOGLE_AD_MANAGER:
                    case Constant.FAN_BIDDING_AD_MANAGER:
                        FrameLayout googleAdContainerView = activity.findViewById(R.id.google_ad_banner_view_container);
                        googleAdContainerView.post(() -> {
                            adManagerAdView = new AdManagerAdView(activity);
                            adManagerAdView.setAdUnitId(googleAdManagerBannerId);
                            googleAdContainerView.removeAllViews();
                            googleAdContainerView.addView(adManagerAdView);
                            adManagerAdView.setAdSize(Tools.getAdSize(activity));
                            adManagerAdView.loadAd(Tools.getGoogleAdManagerRequest());
                            adManagerAdView.setAdListener(new AdListener() {
                                @Override
                                public void onAdClicked() {
                                    super.onAdClicked();
                                }

                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                }

                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    super.onAdFailedToLoad(loadAdError);
                                    googleAdContainerView.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAdImpression() {
                                    super.onAdImpression();
                                }

                                @Override
                                public void onAdLoaded() {
                                    super.onAdLoaded();
                                    googleAdContainerView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onAdOpened() {
                                    super.onAdOpened();
                                }
                            });
                        });
                        break;

                    case Constant.FAN:
                        fanAdView = new com.facebook.ads.AdView(activity, fanBannerId, AdSize.BANNER_HEIGHT_50);
                        RelativeLayout fanAdViewContainer = activity.findViewById(R.id.fan_banner_view_container);
                        fanAdViewContainer.addView(fanAdView);
                        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                            @Override
                            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                                fanAdViewContainer.setVisibility(View.GONE);
                                Log.d(TAG, "Error load FAN : " + adError.getErrorMessage());
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                fanAdViewContainer.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        };
                        com.facebook.ads.AdView.AdViewLoadConfig loadAdConfig = fanAdView.buildLoadAdConfig().withAdListener(adListener).build();
                        fanAdView.loadAd(loadAdConfig);
                        break;
                }
                Log.d(TAG, "Banner Ad is enabled");
            } else {
                Log.d(TAG, "Banner Ad is disabled");
            }
        }

    }

}
