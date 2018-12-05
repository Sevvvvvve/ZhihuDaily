package com.vaynefond.zhihudaily.base.rx.observer;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class BaseSubscriber<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable t) {
    }

    @Override
    public void onComplete() {
    }
}
