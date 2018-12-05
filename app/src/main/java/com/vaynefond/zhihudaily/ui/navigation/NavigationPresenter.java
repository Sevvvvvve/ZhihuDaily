package com.vaynefond.zhihudaily.ui.navigation;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.base.rx.observer.MvpMaybeObserver;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class NavigationPresenter extends BasePresenterImpl<NavigationContract.View> implements NavigationContract.Presenter {
    @Inject
    public NavigationPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadThemes() {
        mDataManager.loadThemes()
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<List<Theme>>(this) {
                    @Override
                    public void onSuccessResult(List<Theme> themes) {
                        getView().showThemes(themes);
                    }
                });
    }
}
