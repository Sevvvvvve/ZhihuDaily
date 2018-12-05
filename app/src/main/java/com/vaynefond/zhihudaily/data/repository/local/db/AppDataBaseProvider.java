package com.vaynefond.zhihudaily.data.repository.local.db;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.TopStory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;

public class AppDataBaseProvider implements DataBaseProvider {
    private final AppDataBase mAppDataBase;

    @Inject
    public AppDataBaseProvider(AppDataBase appDataBase) {
        mAppDataBase = appDataBase;
    }

    @Override
    public void insertStory(@NonNull Story story) {
        mAppDataBase.storyDao().insertStory(story);
    }

    @Override
    public void insertStories(@NonNull List<Story> stories) {
        mAppDataBase.storyDao().insertStories(stories);
    }

    @Override
    public void insertTopStory(TopStory topStory) {
        mAppDataBase.topStoryDao().insertTopStory(topStory);
    }

    @Override
    public void insertTopStories(List<TopStory> topStories) {
        mAppDataBase.topStoryDao().insertTopStories(topStories);
    }

    @Override
    public void insertStoryContent(@NonNull StoryContent storyContent) {
        mAppDataBase.storyContentDao().insertStoryContent(storyContent);
    }

    @Override
    public void insertTheme(@NonNull Theme theme) {
        mAppDataBase.themeDao().insertTheme(theme);
    }

    @Override
    public void insertThemes(@NonNull List<Theme> themes) {
        mAppDataBase.themeDao().insertThemes(themes);
    }

    @Override
    public Maybe<Story> loadStory(int id) {
        return mAppDataBase.storyDao().loadStory(id);
    }

    @Override
    public Maybe<List<Story>> loadStories() {
        return mAppDataBase.storyDao().loadStories();
    }

    @Override
    public Maybe<List<Story>> loadFavoriteStories() {
        return mAppDataBase.storyDao().loadFavoriteStories();
    }

    @Override
    public Maybe<TopStory> loadTopStory(int id) {
        return mAppDataBase.topStoryDao().loadTopStory(id);
    }

    @Override
    public Maybe<List<TopStory>> loadTopStories() {
        return mAppDataBase.topStoryDao().loadTopStories();
    }

    @Override
    public Maybe<StoryContent> loadStoryContent(int id) {
        return mAppDataBase.storyContentDao().loadStoryContent(id);
    }

    @Override
    public Maybe<Theme> loadTheme(int id) {
        return mAppDataBase.themeDao().loadTheme(id);
    }

    @Override
    public Maybe<List<Theme>> loadThemes() {
        return mAppDataBase.themeDao().loadThemes();
    }
}
