package com.vaynefond.zhihudaily.data.repository.local.db;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.TopStory;

import java.util.List;

import io.reactivex.Maybe;

public interface DataBaseProvider {
    void insertStory(@NonNull Story story);

    void insertStories(@NonNull List<Story> stories);

    void insertTopStory(TopStory topStory);

    void insertTopStories(List<TopStory> topStories);

    void insertStoryContent(@NonNull StoryContent storyContent);

    void insertTheme(@NonNull Theme theme);

    void insertThemes(@NonNull List<Theme> themes);

    Maybe<Story> loadStory(int id);

    Maybe<List<Story>> loadStories();

    Maybe<List<Story>> loadFavoriteStories();

    Maybe<TopStory> loadTopStory(int id);

    Maybe<List<TopStory>> loadTopStories();

    Maybe<StoryContent> loadStoryContent(int id);

    Maybe<Theme> loadTheme(int id);

    Maybe<List<Theme>> loadThemes();
}
