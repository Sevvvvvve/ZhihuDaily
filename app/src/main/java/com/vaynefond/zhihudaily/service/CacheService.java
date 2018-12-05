package com.vaynefond.zhihudaily.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.rx.observer.BaseSubscriber;
import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.utils.ComponentUtils;

import javax.inject.Inject;

import dagger.android.DaggerService;

public class CacheService extends DaggerService {
    public static final String ACTION_OFFLINE_DOWNLOAD =
            "com.vaynefond.zhihudaily.service.offline.action.OFFLINE_DOWNLOAD";
    public static final String ACTION_CLEAR_CACHE =
            "com.vaynefond.zhihudaily.service.offline.action.CLEAR_CACHE";

    public class ServiceController extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(ACTION_OFFLINE_DOWNLOAD)) {
                CacheService.this.handleOfflineDownload();
            } else if (action.equals(ACTION_CLEAR_CACHE)) {
                CacheService.this.handleClearCache();
            }
        }
    }

    private final ServiceController mServiceController = new ServiceController();

    @Inject
    CacheManager mCacheManager;

    public static void start(@NonNull Context context) {
        ComponentUtils.startService(context, CacheService.class);
    }

    public static void stop(@NonNull Context context) {
        ComponentUtils.stopService(context, CacheService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_OFFLINE_DOWNLOAD);
        LocalBroadcastManager.getInstance(this).registerReceiver(mServiceController, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void handleOfflineDownload() {
        mCacheManager.offlineDownload(new BaseSubscriber() {
            @Override
            public void onComplete() {
                AppToast.showLongText(R.string.offline_download_complete);
            }
        });
    }


    private void handleClearCache() {
        mCacheManager.clearCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mServiceController);
    }
}
