package com.vaynefond.zhihudaily.di.component;

import android.app.Application;

import com.vaynefond.zhihudaily.app.ZhihuAppImpl;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.repository.DataRepository;
import com.vaynefond.zhihudaily.di.module.ActivityModule;
import com.vaynefond.zhihudaily.di.module.AppDataManagerModule;
import com.vaynefond.zhihudaily.di.module.AppModule;
import com.vaynefond.zhihudaily.di.module.AppRepositoryModule;
import com.vaynefond.zhihudaily.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppDataManagerModule.class,
        AppRepositoryModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        ServiceModule.class,
        AppModule.class })
public interface AppComponent extends AndroidInjector<ZhihuAppImpl> {
    DataManager getDataManager();

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
