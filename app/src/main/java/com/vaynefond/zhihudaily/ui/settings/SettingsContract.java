package com.vaynefond.zhihudaily.ui.settings;

import android.content.Context;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;

public interface SettingsContract {
    interface View extends BaseView {
        void updateCacheSummary(long cacheSize);

        void notifyCacheCleared(boolean succeed);

        void notifyVersionChecked();

        void notifyFeedback();
    }

    interface Presenter extends BasePresenter<View> {
        boolean loadAutoOfflinePref();

        void setAutoOfflinePref(boolean autoOffline);

        boolean loadNoImageModePref();

        void setNoImageModePref(boolean noImageMode);

        boolean loadBigFontPref();

        void setBigFontPref(boolean bigFont);

        boolean loadPushPref();

        void setPushPref(boolean push);

        boolean loadCommentSharePref();

        void setCommentSharePref(boolean commentShare);

        void loadCacheSize();

        void clearCache();

        String loadVersionNumber();

        void checkVersionUpdate();

        void feedback();
    }
}
