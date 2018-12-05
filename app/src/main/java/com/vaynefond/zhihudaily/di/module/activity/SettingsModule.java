package com.vaynefond.zhihudaily.di.module.activity;

import com.vaynefond.zhihudaily.data.AppDataManager;
import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.di.Cache;
import com.vaynefond.zhihudaily.di.FragmentScoped;
import com.vaynefond.zhihudaily.service.CacheManager;
import com.vaynefond.zhihudaily.ui.settings.SettingsContract;
import com.vaynefond.zhihudaily.ui.settings.SettingsFragment;
import com.vaynefond.zhihudaily.ui.settings.SettingsPresenter;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract SettingsFragment settingsFragment();

    @ActivityScoped
    @Binds
    abstract SettingsContract.Presenter settingsPresenter(SettingsPresenter settingsPresenter);

    @Singleton
    @Provides
    @Cache
    static CacheManager provideCacheManager(AppDataManager dataManager) {
        return new CacheManager(dataManager);
    }
}
