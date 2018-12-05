package com.vaynefond.zhihudaily.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.vaynefond.zhihudaily.data.repository.local.LocalDataRepository;
import com.vaynefond.zhihudaily.data.repository.local.db.AppDataBase;
import com.vaynefond.zhihudaily.data.repository.local.db.AppDataBaseProvider;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.StoryContentDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.StoryDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.StoryExtraDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.ThemeDao;
import com.vaynefond.zhihudaily.data.repository.local.db.dao.TopStoryDao;
import com.vaynefond.zhihudaily.data.repository.remote.RemoteDataRepository;
import com.vaynefond.zhihudaily.data.repository.remote.api.Api;
import com.vaynefond.zhihudaily.data.repository.remote.api.ApiService;
import com.vaynefond.zhihudaily.di.Local;
import com.vaynefond.zhihudaily.di.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppRepositoryModule {
    @Singleton
    @Provides
    @Local
    LocalDataRepository provideLocalDataRepository(AppDataBase dataBase) {
        return new LocalDataRepository(dataBase);
    }

    @Singleton
    @Provides
    @Remote
    RemoteDataRepository provideRemoteDataRepository(Api api) {
        return new RemoteDataRepository(api);
    }

    @Singleton
    @Provides
    static AppDataBase provideDataBase(Application application) {
        return Room.databaseBuilder(application, AppDataBase.class, AppDataBase.NAME).build();
    }

    @Singleton
    @Provides
    static AppDataBaseProvider provideAppDataBaseProvider(AppDataBase appDataBase) {
        return new AppDataBaseProvider(appDataBase);
    }

    @Singleton
    @Provides
    static StoryDao provideStoryDao(AppDataBase dataBase) {
        return dataBase.storyDao();
    }

    @Singleton
    @Provides
    static TopStoryDao provideTopStoryDao(AppDataBase dataBase) {
        return dataBase.topStoryDao();
    }

    @Singleton
    @Provides
    static StoryExtraDao provideStoryExtraDao(AppDataBase dataBase) {
        return dataBase.storyExtraDao();
    }

    @Singleton
    @Provides
    static StoryContentDao provideStoryContentDao(AppDataBase dataBase) {
        return dataBase.storyContentDao();
    }

    @Singleton
    @Provides
    static ThemeDao provideThemeDao(AppDataBase dataBase) {
        return dataBase.themeDao();
    }

    @Singleton
    @Provides
    static Api provideApi(ApiService apiService) {
        return apiService;
    }
}
