package com.vaynefond.zhihudaily.di.module.activity;

import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.di.FragmentScoped;
import com.vaynefond.zhihudaily.ui.comment.CommentContract;
import com.vaynefond.zhihudaily.ui.comment.CommentFragment;
import com.vaynefond.zhihudaily.ui.comment.CommentPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CommentModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract CommentFragment commentFragment();

    @ActivityScoped
    @Binds
    abstract CommentContract.Presenter commentPresenter(CommentPresenter commentPresenter);
}
