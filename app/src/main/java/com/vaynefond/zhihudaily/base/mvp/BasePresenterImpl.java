package com.vaynefond.zhihudaily.base.mvp;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.DataManager;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenterImpl<V> implements BasePresenter<V> {
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected final DataManager mDataManager;
    private WeakReference<V> mViewRef;

    @Inject
    public BasePresenterImpl(DataManager dataManager) {
        mDataManager = dataManager;
    }

    protected void addDisposable(@NonNull Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void attachView(@NonNull V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }


    @Override
    public V getView() {
        if (!isViewAttached()) {
            throw new ViewNotAttachedException();
        }

        return mViewRef.get();
    }

    @Override
    public void detachView() {
        mCompositeDisposable.clear();
        if (isViewAttached()) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public boolean isNightMode() {
        return mDataManager.isNightMode();
    }

    public boolean isNoImageMode() {
        return mDataManager.isNoImageMode();
    }

    public static class ViewNotAttachedException extends RuntimeException {
        ViewNotAttachedException() {
            super("Please call Presenter.attachView(V) before requesting data from presenter");
        }
    }
}
