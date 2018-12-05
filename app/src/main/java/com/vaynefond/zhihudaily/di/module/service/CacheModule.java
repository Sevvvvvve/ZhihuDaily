package com.vaynefond.zhihudaily.di.module.service;

import com.vaynefond.zhihudaily.data.AppDataManager;
import com.vaynefond.zhihudaily.di.Cache;
import com.vaynefond.zhihudaily.service.CacheManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {
    @Singleton
    @Provides
    @Cache
    CacheManager provideCacheManager(AppDataManager dataManager) {
        return new CacheManager(dataManager);
    }
}
