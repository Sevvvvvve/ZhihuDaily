package com.vaynefond.zhihudaily.utils;

import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {
    public static <T> ObservableTransformer<T, T> ioMainObservable() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> FlowableTransformer<T, T> ioMainFlowable() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> MaybeTransformer<T, T> ioMainMaybe() {
        return upstream -> upstream.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public static <T> MaybeTransformer<T, T> ioIoMaybe() {
        return upstream -> upstream.observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io());
    }
}
