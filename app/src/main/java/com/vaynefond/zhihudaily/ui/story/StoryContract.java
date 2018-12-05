package com.vaynefond.zhihudaily.ui.story;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;

public interface StoryContract {
    interface View extends BaseView {
        void showStoryContent(@NonNull StoryContent content);

        void showStoryExtra(@NonNull StoryExtra storyExtra);

        void updateFavoriteView(boolean isFavorite);

        void showShareView(String title, String url);
    }

    interface Presenter extends BasePresenter<View> {
        void loadStoryContent(int storyId);

        void loadStoryExtra(int storyId);

        void loadShareLink(int storyId);

        void loadFavoriteValue(int storyId);

        void favoriteStory(int storyId, boolean favorite);
    }
}
