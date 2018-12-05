package com.vaynefond.zhihudaily.ui.comment;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;
import com.vaynefond.zhihudaily.data.model.Comment;

import java.util.List;

public interface CommentContract {
    interface View extends BaseView {
        void showCommentsExtra(int totalCommentCount);

        void showLongComments(@NonNull List<Comment> comments);

        void showShortComments(@NonNull List<Comment> comments);
    }

    interface Presenter extends BasePresenter<View> {
        void loadCommentExtra(int storyId);

        void loadLongComments(int storyId);

        void loadShortComments(int storyId);
    }
}
