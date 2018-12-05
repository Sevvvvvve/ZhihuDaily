package com.vaynefond.zhihudaily.ui.splash;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;

public interface SplashContract {
    interface View extends BaseView {
        void openDailyPaper();
    }

    interface Presenter extends BasePresenter<View> {
        boolean isAutoOffline();
    }
}
