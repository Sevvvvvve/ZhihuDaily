package com.vaynefond.zhihudaily.ui.favorite;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.base.rx.observer.MvpMaybeObserver;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class FavoritePresenter extends BasePresenterImpl<FavoriteContract.View>
        implements FavoriteContract.Presenter {
    @Inject
    public FavoritePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadFavoriteStories() {
        mDataManager.loadFavoriteStories()
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<List<Story>>(this) {
                    @Override
                    public void onSuccessResult(List<Story> stories) {
                        getView().showFavoriteStories(stories);
                    }
                });
    }
}
