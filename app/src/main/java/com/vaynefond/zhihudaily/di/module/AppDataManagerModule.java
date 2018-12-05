package com.vaynefond.zhihudaily.di.module;

import android.app.Application;
import android.content.Context;

import com.vaynefond.zhihudaily.app.ZhihuAppImpl;
import com.vaynefond.zhihudaily.data.preference.AppPreferenceProvider;
import com.vaynefond.zhihudaily.data.preference.PreferenceProvider;
import com.vaynefond.zhihudaily.data.repository.AppDataRepository;
import com.vaynefond.zhihudaily.data.repository.DataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppDataManagerModule {
    @Singleton
    @Provides
    DataRepository provideDataRepository(AppDataRepository appDataRepository) {
        return appDataRepository;
    }

    @Singleton
    @Provides
    PreferenceProvider providePreferenceProvider(Application application) {
        return new AppPreferenceProvider(application, ZhihuAppImpl.PREF_FILE_NAME);
    }
}
