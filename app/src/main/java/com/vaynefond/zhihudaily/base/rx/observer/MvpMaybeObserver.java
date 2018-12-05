package com.vaynefond.zhihudaily.base.rx.observer;

import com.vaynefond.zhihudaily.base.mvp.BasePresenter;
import com.vaynefond.zhihudaily.base.mvp.BaseView;

public abstract class MvpMaybeObserver<T> extends BaseMaybeObserver<T> {
    private BasePresenter mPresenter;

    public MvpMaybeObserver(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onSuccess(T t) {
        if (!mPresenter.isViewAttached()) {
            return;
        }
        onSuccessResult(t);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        if (!mPresenter.isViewAttached()) {
            return;
        }

        if (mPresenter.getView() instanceof BaseView) {
            ((BaseView) mPresenter.getView()).onError(errorCode, errorMsg);
        }
    }

    public abstract void onSuccessResult(T t);
}
