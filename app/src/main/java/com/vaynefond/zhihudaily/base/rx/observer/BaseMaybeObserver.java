package com.vaynefond.zhihudaily.base.rx.observer;

import com.vaynefond.zhihudaily.base.net.NetworkException;

import io.reactivex.observers.DisposableMaybeObserver;

public abstract class BaseMaybeObserver<T> extends DisposableMaybeObserver<T> {
    @Override
    protected void onStart() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable throwable) {
        NetworkException exception = NetworkException.create(throwable);
        onError(exception.getErrorCode(), exception.getErrorMsg());
    }

    public abstract void onError(int errorCode, String errorMsg);
}