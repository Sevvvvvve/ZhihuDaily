package com.vaynefond.zhihudaily.data.repository.remote;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.Version;
import com.vaynefond.zhihudaily.data.repository.DataRepository;
import com.vaynefond.zhihudaily.data.repository.remote.api.Api;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class RemoteDataRepository implements DataRepository {
    private final Api mApi;

    @Inject
    public RemoteDataRepository(@NonNull Api api) {
        mApi = api;
    }

    @Override
    public Maybe<Version> loadLatestVersion() {
        return mApi.getLatestVersion();
    }

    @Override
    public Maybe<LatestPaper> loadLatestPaper() {
        return mApi.getLatestPaper();
    }

    @Override
    public Maybe<Paper> loadStoriesBefore(String date) {
        return mApi.getStoriesBefore(date);
    }

    @Override
    public Maybe<StoryContent> loadStoryContent(int storyId) {
        return mApi.getStoryContent(storyId);
    }

    @Override
    public Maybe<StoryExtra> loadStoryExtra(int storyId) {
        return mApi.getStoryExtra(storyId);
    }

    @Override
    public Maybe<Theme> loadTheme(int id) {
        return mApi.getTheme(id);
    }

    @Override
    public Maybe<List<Theme>> loadThemes() {
        return mApi.getThemes();
    }

    @Override
    public Maybe<List<Comment>> loadLongComments(int storyId) {
        return mApi.getLongComments(storyId);
    }

    @Override
    public Maybe<List<Comment>> loadShortComments(int storyId) {
        return mApi.getShortComments(storyId);
    }

    @Override
    public Maybe<List<Story>> loadFavoriteStories() {
        return Maybe.empty();
    }

    @Override
    public Maybe<Story> loadStory(int storyId) {
        return Maybe.empty();
    }

    @Override
    public Maybe<Boolean> favoriteStory(int storyId, boolean favorite) {
        return Maybe.empty();
    }

    @Override
    public Maybe<Boolean> clearDatabase() {
        return Maybe.empty();
    }
}
