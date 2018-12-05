package com.vaynefond.zhihudaily.ui.story;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.base.rx.observer.MvpMaybeObserver;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Function;

public class StoryPresenter extends BasePresenterImpl<StoryContract.View> implements StoryContract.Presenter {
    @Inject
    public StoryPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadStoryContent(int storyId) {
        mDataManager.loadStoryContent(storyId)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<StoryContent>(this) {
                    @Override
                    public void onSuccessResult(StoryContent storyContent) {
                        getView().showStoryContent(storyContent);
                    }
                });
    }

    @Override
    public void loadStoryExtra(int storyId) {
        mDataManager.loadStoryExtra(storyId)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<StoryExtra>(this) {
                    @Override
                    public void onSuccessResult(StoryExtra storyExtra) {
                        getView().showStoryExtra(storyExtra);
                    }
                });
    }

    @Override
    public void loadShareLink(int storyId) {
        mDataManager.loadStoryContent(storyId)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<StoryContent>(this) {
                    @Override
                    public void onSuccessResult(StoryContent content) {
                        getView().showShareView(content.getTitle(), content.getShareUrl());
                    }
                });
    }

    @Override
    public void loadFavoriteValue(int storyId) {
        mDataManager.loadStory(storyId)
                .compose(RxUtils.ioMainMaybe())
                .flatMap((Function<Story, MaybeSource<Boolean>>) story -> Maybe.just(story.isFavorite()))
                .subscribe(new MvpMaybeObserver<Boolean>(this) {
                    @Override
                    public void onSuccessResult(Boolean isFavorite) {
                        getView().updateFavoriteView(isFavorite);
                    }
                });
    }

    @Override
    public void favoriteStory(int storyId, boolean favorite) {
        mDataManager.favoriteStory(storyId, favorite)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<Boolean>(this) {
                    @Override
                    public void onSuccessResult(Boolean favorite) {
                        getView().updateFavoriteView(favorite);
                    }
                });
    }
}
