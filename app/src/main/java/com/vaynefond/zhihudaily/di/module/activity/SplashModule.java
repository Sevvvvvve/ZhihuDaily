package com.vaynefond.zhihudaily.di.module.activity;

import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.ui.splash.SplashContract;
import com.vaynefond.zhihudaily.ui.splash.SplashPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SplashModule {
    @ActivityScoped
    @Binds
    abstract SplashContract.Presenter splashPresenter(SplashPresenter presenter);
}
