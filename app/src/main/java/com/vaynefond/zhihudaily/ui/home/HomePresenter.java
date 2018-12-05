package com.vaynefond.zhihudaily.ui.home;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.base.net.NoDataException;
import com.vaynefond.zhihudaily.base.rx.observer.MvpMaybeObserver;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.MaybeSource;

@ActivityScoped
public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {
    @Inject
    HomePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadLatestPaper() {
        mDataManager.loadLatestPaper()
                .compose(RxUtils.ioMainMaybe())
                .switchIfEmpty((MaybeSource<? extends LatestPaper>) observer -> observer.onError(NoDataException.create()))
                .subscribe(new MvpMaybeObserver<LatestPaper>(this) {
                    @Override
                    public void onSuccessResult(LatestPaper latestPaper) {
                        getView().showTopPaper(latestPaper.getTopStories());
                        getView().showPaper(Paper.create(latestPaper.getDate(), latestPaper.getStories()));
                    }
                });
    }

    @Override
    public void loadStoriesByDate(String date) {
        mDataManager.loadStoriesBefore(date)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<Paper>(this) {
                    @Override
                    public void onSuccessResult(Paper paper) {
                        getView().showPaper(paper);
                    }
                });
    }
}
