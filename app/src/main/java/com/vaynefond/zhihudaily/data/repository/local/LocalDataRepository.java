package com.vaynefond.zhihudaily.data.repository.local;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.TopStory;
import com.vaynefond.zhihudaily.data.model.Version;
import com.vaynefond.zhihudaily.data.repository.DataRepository;
import com.vaynefond.zhihudaily.data.repository.local.db.AppDataBase;
import com.vaynefond.zhihudaily.utils.DateUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class LocalDataRepository implements DataRepository {
    private final AppDataBase mAppDataBase;

    @Inject
    public LocalDataRepository(@NonNull AppDataBase appDataBase) {
        mAppDataBase = appDataBase;
    }

    @Override
    public Maybe<Version> loadLatestVersion() {
        return Maybe.empty();
    }

    @Override
    public Maybe<LatestPaper> loadLatestPaper() {
        return Maybe.zip(mAppDataBase.storyDao().loadLatestDate(),
                mAppDataBase.storyDao().loadLatestStories(),
                mAppDataBase.topStoryDao().loadTopStories(),
                LatestPaper::create);
    }

    @Override
    public Maybe<Paper> loadStoriesBefore(String date) {
        return mAppDataBase.storyDao()
                .loadStoriesByDate(DateUtils.getBeforeDay(date))
                .filter(stories -> stories != null && !stories.isEmpty())
                .flatMap(stories -> Maybe.just(Paper.create(stories)));
    }

    @Override
    public Maybe<StoryContent> loadStoryContent(int storyId) {
        return mAppDataBase.storyContentDao().loadStoryContent(storyId);
    }

    @Override
    public Maybe<StoryExtra> loadStoryExtra(int storyId) {
        return mAppDataBase.storyExtraDao().loadStoryExtra(storyId);
    }

    @Override
    public Maybe<Theme> loadTheme(int id) {
        return mAppDataBase.themeDao().loadTheme(id);
    }

    @Override
    public Maybe<List<Theme>> loadThemes() {
        return mAppDataBase.themeDao().loadThemes();
    }

    @Override
    public Maybe<List<Comment>> loadLongComments(int storyId) {
        return Maybe.empty();
    }

    @Override
    public Maybe<List<Comment>> loadShortComments(int storyId) {
        return Maybe.empty();
    }

    @Override
    public Maybe<List<Story>> loadFavoriteStories() {
        return mAppDataBase.storyDao().loadFavoriteStories();
    }

    @Override
    public Maybe<Story> loadStory(int storyId) {
        return mAppDataBase.storyDao().loadStory(storyId);
    }

    @Override
    public Maybe<Boolean> favoriteStory(int storyId, boolean favorite) {
        return Maybe.just(favorite)
                .doOnSubscribe(disposable -> mAppDataBase.storyDao().favoriteStory(storyId, favorite ? 1 : 0));
    }

    @Override
    public Maybe<Boolean> clearDatabase() {
        return Maybe.just(true).doOnSubscribe(disposable -> mAppDataBase.clearAllTables());
    }

    public void cacheLatestPaper(@NonNull LatestPaper latestPaper) {
        cacheTopStories(latestPaper.getTopStories());
        cacheStories(latestPaper.getStories());
    }

    public void cacheStories(@NonNull List<Story> stories) {
        mAppDataBase.storyDao().insertStories(stories);
    }

    public void cacheTopStories(@NonNull List<TopStory> topStories) {
        mAppDataBase.topStoryDao().insertTopStories(topStories);
    }

    public void cacheStoryContent(@NonNull StoryContent storyContent) {
        mAppDataBase.storyContentDao().insertStoryContent(storyContent);
    }

    public void cacheStoryExtra(@NonNull StoryExtra storyExtra) {
        mAppDataBase.storyExtraDao().insertStoryExtra(storyExtra);
    }

    public void cacheThemes(@NonNull List<Theme> themes) {
        mAppDataBase.themeDao().insertThemes(themes);
    }
}
