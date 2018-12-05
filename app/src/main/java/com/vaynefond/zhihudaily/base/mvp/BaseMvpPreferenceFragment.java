package com.vaynefond.zhihudaily.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vaynefond.zhihudaily.base.widget.AppToast;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseMvpPreferenceFragment<T extends BasePresenter<V>, V extends BaseView>
        extends PreferenceFragmentCompat implements HasSupportFragmentInjector, BaseView {
    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Inject
    protected T mPresenter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
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
