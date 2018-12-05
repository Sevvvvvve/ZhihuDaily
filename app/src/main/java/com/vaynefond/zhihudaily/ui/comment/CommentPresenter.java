package com.vaynefond.zhihudaily.ui.comment;

import com.vaynefond.zhihudaily.base.mvp.BasePresenterImpl;
import com.vaynefond.zhihudaily.base.rx.observer.MvpMaybeObserver;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

public class CommentPresenter extends BasePresenterImpl<CommentContract.View>
        implements CommentContract.Presenter {
    @Inject
    public CommentPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadCommentExtra(int storyId) {
        mDataManager.loadStoryExtra(storyId)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<StoryExtra>(this) {
                    @Override
                    public void onSuccessResult(StoryExtra storyExtra) {
                        getView().showCommentsExtra(storyExtra.getComments());
                    }
                });
    }

    @Override
    public void loadLongComments(int storyId) {
        mDataManager.loadLongComments(storyId)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<List<Comment>>(this) {
                    @Override
                    public void onSuccessResult(List<Comment> comments) {
                        getView().showLongComments(comments);
                    }
                });
    }

    @Override
    public void loadShortComments(int storyId) {
        mDataManager.loadShortComments(storyId)
                .compose(RxUtils.ioMainMaybe())
                .subscribe(new MvpMaybeObserver<List<Comment>>(this) {
                    @Override
                    public void onSuccessResult(List<Comment> comments) {
                        getView().showShortComments(comments);
                    }
                });
    }
}
