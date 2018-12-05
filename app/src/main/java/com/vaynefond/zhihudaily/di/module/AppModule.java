package com.vaynefond.zhihudaily.di.module;

import android.app.Application;
import android.content.Context;

import com.vaynefond.zhihudaily.data.AppDataManager;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.repository.AppDataRepository;
import com.vaynefond.zhihudaily.data.repository.DataRepository;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(Application application);

    @Binds
    abstract DataManager bindDataManager(AppDataManager appDataManager);
}
