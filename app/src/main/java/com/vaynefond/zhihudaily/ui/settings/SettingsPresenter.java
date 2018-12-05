package com.vaynefond.zhihudaily.ui.settings;

import android.content.Context;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.base.rx.observer.MvpMaybeObserver;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.service.CacheManager;
import com.vaynefond.zhihudaily.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class SettingsPresenter extends BasePresenterImpl<SettingsContract.View>
        implements SettingsContract.Presenter {
    @Inject
    CacheManager mCacheManager;

    @Inject
    public SettingsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public boolean loadAutoOfflinePref() {
        return mDataManager.isAutoOffline();
    }

    @Override
    public void setAutoOfflinePref(boolean autoOffline) {
        mDataManager.setAutoOffline(autoOffline);
    }

    @Override
    public boolean loadNoImageModePref() {
        return mDataManager.isNoImageMode();
    }

    @Override
    public void setNoImageModePref(boolean noImageMode) {
        mDataManager.setNoImageMode(noImageMode);
    }

    @Override
    public boolean loadBigFontPref() {
        return mDataManager.isBigFontMode();
    }

    @Override
    public void setBigFontPref(boolean bigFont) {
        mDataManager.setBigFontMode(bigFont);
    }

    @Override
    public boolean loadPushPref() {
        return mDataManager.isAutoPush();
    }

    @Override
    public void setPushPref(boolean push) {
        mDataManager.setAutoPush(push);
    }

    @Override
    public boolean loadCommentSharePref() {
        return mDataManager.isCommentShare();
    }

    @Override
    public void setCommentSharePref(boolean commentShare) {
        mDataManager.setCommentShare(commentShare);
    }

    @Override
    public void loadCacheSize() {
        mCacheManager.getCacheSize()
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<Long>(this) {
                    @Override
                    public void onSuccessResult(Long size) {
                        getView().updateCacheSummary(size);
                    }
                });
    }

    @Override
    public void clearCache() {
        mCacheManager.clearCache()
                .compose(RxUtils.ioMainMaybe())
                .doAfterSuccess(succeed -> loadCacheSize())
                .subscribe(new MvpMaybeObserver<Boolean>(this) {
                    @Override
                    public void onSuccessResult(Boolean succeed) {
                        getView().notifyCacheCleared(succeed);
                    }
                });
    }

    @Override
    public String loadVersionNumber() {
        return mDataManager.getVersionNumber();
    }

    @Override
    public void checkVersionUpdate() {

    }

    @Override
    public void feedback() {

    }
}
