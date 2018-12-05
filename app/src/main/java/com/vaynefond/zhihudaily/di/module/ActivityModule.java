package com.vaynefond.zhihudaily.di.module;

import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.di.module.activity.CommentModule;
import com.vaynefond.zhihudaily.di.module.activity.DailyModule;
import com.vaynefond.zhihudaily.di.module.activity.SettingsModule;
import com.vaynefond.zhihudaily.di.module.activity.SplashModule;
import com.vaynefond.zhihudaily.di.module.activity.StoryModule;
import com.vaynefond.zhihudaily.ui.comment.CommentActivity;
import com.vaynefond.zhihudaily.ui.home.DailyActivity;
import com.vaynefond.zhihudaily.ui.settings.SettingsActivity;
import com.vaynefond.zhihudaily.ui.splash.SplashActivity;
import com.vaynefond.zhihudaily.ui.story.StoryActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = DailyModule.class)
    abstract DailyActivity dailyActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SplashModule.class)
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = StoryModule.class)
    abstract StoryActivity storyActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = CommentModule.class)
    abstract CommentActivity commentActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsActivity settingsActivity();
}
