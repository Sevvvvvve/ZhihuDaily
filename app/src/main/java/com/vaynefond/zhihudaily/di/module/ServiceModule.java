package com.vaynefond.zhihudaily.di.module;

import com.vaynefond.zhihudaily.di.ServiceScoped;
import com.vaynefond.zhihudaily.di.module.service.CacheModule;
import com.vaynefond.zhihudaily.service.CacheService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {
    @ServiceScoped
    @ContributesAndroidInjector(modules = CacheModule.class)
    abstract CacheService cacheService();
}
