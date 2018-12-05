package com.vaynefond.zhihudaily.ui.splash;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class SplashPresenter extends BasePresenterImpl<SplashContract.View> implements SplashContract.Presenter {
    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean isAutoOffline() {
        return mDataManager.isAutoOffline();
    }
}
