package com.vaynefond.zhihudaily.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vaynefond.zhihudaily.base.app.BaseFragment;
import com.vaynefond.zhihudaily.base.widget.AppToast;

import javax.inject.Inject;

public abstract class BaseMvpFragment<T extends BasePresenter<V>, V extends BaseView>
        extends BaseFragment implements BaseView {
    @Inject
    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        mPresenter.attachView((V)this);
        return root;
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        AppToast.showLongText(errorMsg);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }
}
