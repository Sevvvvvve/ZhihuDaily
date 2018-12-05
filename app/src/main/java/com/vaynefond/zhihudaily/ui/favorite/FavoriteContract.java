package com.vaynefond.zhihudaily.ui.favorite;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;
import com.vaynefond.zhihudaily.data.model.Story;

import java.util.List;

public interface FavoriteContract {
    interface View extends BaseView {
        void showFavoriteStories(List<Story> stories);
    }

    interface Presenter extends BasePresenter<View> {
        void loadFavoriteStories();
    }
}
