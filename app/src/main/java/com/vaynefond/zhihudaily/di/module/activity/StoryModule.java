package com.vaynefond.zhihudaily.di.module.activity;

import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.di.FragmentScoped;
import com.vaynefond.zhihudaily.ui.story.StoryContract;
import com.vaynefond.zhihudaily.ui.story.StoryFragment;
import com.vaynefond.zhihudaily.ui.story.StoryPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StoryModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract StoryFragment storyFragment();

    @ActivityScoped
    @Binds
    abstract StoryContract.Presenter storyPresenter(StoryPresenter storyPresenter);
}
