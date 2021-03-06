package com.vaynefond.zhihudaily.base.mvp;

import android.support.annotation.NonNull;

public interface BasePresenter<V> {
    void attachView(@NonNull V view);

    boolean isViewAttached();

    V getView();

    void detachView();
}
