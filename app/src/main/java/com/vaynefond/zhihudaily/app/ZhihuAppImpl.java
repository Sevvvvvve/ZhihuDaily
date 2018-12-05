package com.vaynefond.zhihudaily.app;

import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.di.component.DaggerAppComponent;
import com.vaynefond.zhihudaily.utils.AppContext;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class ZhihuAppImpl extends DaggerApplication implements ZhihuApp {
    public static final String PREF_FILE_NAME = "zhihu_pref";

    @Inject
    DataManager mDataManager;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.initialize(this);
        AppToast.initialize(this);
    }

    @Override
    public DataManager getDataManager() {
        return mDataManager;
    }

    @Override
    public boolean isNightMode() {
        return mDataManager.isNightMode();
    }

    @Override
    public void setNightMode(boolean nightMode) {
        mDataManager.setNightMode(nightMode);
    }
}
