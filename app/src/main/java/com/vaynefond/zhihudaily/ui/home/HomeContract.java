package com.vaynefond.zhihudaily.ui.home;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.TopStory;

import java.util.List;

public interface HomeContract {
    interface View extends BaseView {
        void showTopPaper(@NonNull List<TopStory> topStories);

        void showPaper(@NonNull Paper paper);
    }

    interface Presenter extends BasePresenter<View> {
        void loadLatestPaper();

        void loadStoriesByDate(String date);
    }
}
