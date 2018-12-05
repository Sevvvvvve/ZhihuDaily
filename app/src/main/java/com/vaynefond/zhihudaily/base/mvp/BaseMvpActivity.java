package com.vaynefond.zhihudaily.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.vaynefond.zhihudaily.base.app.BaseActivity;

import javax.inject.Inject;

public abstract class BaseMvpActivity<T extends BasePresenter<V>, V extends BaseView> extends BaseActivity {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView((V)this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
