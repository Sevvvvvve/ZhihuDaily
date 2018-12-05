package com.vaynefond.zhihudaily.ui.navigation;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;
import com.vaynefond.zhihudaily.data.model.Theme;

import java.util.List;

public interface NavigationContract {
    interface View extends BaseView {
        void showThemes(@NonNull List<Theme> themes);
    }

    interface Presenter extends BasePresenter<View> {
        void loadThemes();
    }
}
