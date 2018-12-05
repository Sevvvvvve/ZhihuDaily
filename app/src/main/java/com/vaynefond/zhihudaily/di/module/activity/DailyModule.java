package com.vaynefond.zhihudaily.di.module.activity;

import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.di.FragmentScoped;
import com.vaynefond.zhihudaily.ui.favorite.FavoriteContract;
import com.vaynefond.zhihudaily.ui.favorite.FavoriteFragment;
import com.vaynefond.zhihudaily.ui.favorite.FavoritePresenter;
import com.vaynefond.zhihudaily.ui.home.HomeContract;
import com.vaynefond.zhihudaily.ui.home.HomeFragment;
import com.vaynefond.zhihudaily.ui.home.HomePresenter;
import com.vaynefond.zhihudaily.ui.navigation.NavigationContract;
import com.vaynefond.zhihudaily.ui.navigation.NavigationFragment;
import com.vaynefond.zhihudaily.ui.navigation.NavigationPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DailyModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

    @ActivityScoped
    @Binds
    abstract HomeContract.Presenter homePresenter(HomePresenter homePresenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NavigationFragment navigationFragment();

    @ActivityScoped
    @Binds
    abstract NavigationContract.Presenter navigationPresenter(NavigationPresenter navigationPresenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract FavoriteFragment favoriteFragment();

    @ActivityScoped
    @Binds
    abstract FavoriteContract.Presenter favoritePresenter(FavoritePresenter favoritePresenter);
}
