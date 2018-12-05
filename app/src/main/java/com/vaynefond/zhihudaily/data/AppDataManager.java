package com.vaynefond.zhihudaily.data;

import android.support.annotation.NonNull;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.Version;
import com.vaynefond.zhihudaily.data.preference.PreferenceProvider;
import com.vaynefond.zhihudaily.data.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class AppDataManager implements DataManager {
    private final DataRepository mDataRepository;
    private final PreferenceProvider mPreferenceProvider;

    @Inject
    public AppDataManager(DataRepository dataRepository,
                          PreferenceProvider preferenceProvider) {
        mDataRepository = dataRepository;
        mPreferenceProvider = preferenceProvider;
    }

    @Override
    public Maybe<Version> loadLatestVersion() {
        return mDataRepository.loadLatestVersion();
    }

    @Override
    public Maybe<LatestPaper> loadLatestPaper() {
        return mDataRepository.loadLatestPaper();
    }

    @Override
    public Maybe<Paper> loadStoriesBefore(String date) {
        return mDataRepository.loadStoriesBefore(date);
    }

    @Override
    public Maybe<StoryContent> loadStoryContent(int storyId) {
        return mDataRepository.loadStoryContent(storyId);
    }

    @Override
    public Maybe<StoryExtra> loadStoryExtra(int storyId) {
        return mDataRepository.loadStoryExtra(storyId);
    }

    @Override
    public Maybe<Theme> loadTheme(int id) {
        return mDataRepository.loadTheme(id);
    }

    @Override
    public Maybe<List<Theme>> loadThemes() {
        return mDataRepository.loadThemes();
    }

    @Override
    public Maybe<List<Comment>> loadLongComments(int storyId) {
        return mDataRepository.loadLongComments(storyId);
    }

    @Override
    public Maybe<List<Comment>> loadShortComments(int storyId) {
        return mDataRepository.loadShortComments(storyId);
    }

    @Override
    public Maybe<List<Story>> loadFavoriteStories() {
        return mDataRepository.loadFavoriteStories();
    }

    @Override
    public Maybe<Story> loadStory(int storyId) {
        return mDataRepository.loadStory(storyId);
    }

    @Override
    public Maybe<Boolean> favoriteStory(int storyId, boolean favorite) {
        return mDataRepository.favoriteStory(storyId, favorite);
    }

    @Override
    public boolean isAutoOffline() {
        return mPreferenceProvider.isAutoOffline();
    }

    @Override
    public void setAutoOffline(boolean autoOffline) {
        mPreferenceProvider.setAutoOffline(autoOffline);
    }

    @Override
    public boolean isNoImageMode() {
        return mPreferenceProvider.isNoImageMode();
    }

    @Override
    public void setNoImageMode(boolean noImageMode) {
        mPreferenceProvider.setNoImageMode(noImageMode);
    }

    @Override
    public boolean isBigFontMode() {
        return mPreferenceProvider.isBigFontMode();
    }

    @Override
    public void setBigFontMode(boolean bigFont) {
        mPreferenceProvider.setBigFontMode(bigFont);
    }

    @Override
    public boolean isAutoPush() {
        return mPreferenceProvider.isAutoPush();
    }

    @Override
    public void setAutoPush(boolean push) {
        mPreferenceProvider.setAutoPush(push);
    }

    @Override
    public boolean isCommentShare() {
        return mPreferenceProvider.isCommentShare();
    }

    @Override
    public void setCommentShare(boolean commentShare) {
        mPreferenceProvider.setCommentShare(commentShare);
    }

    @Override
    public String getVersionNumber() {
        return mPreferenceProvider.getVersionNumber();
    }

    @Override
    public void setVersionNumber(@NonNull String versionNumber) {
        mPreferenceProvider.setVersionNumber(versionNumber);
    }

    @Override
    public void setNightMode(boolean nightMode) {
        mPreferenceProvider.setNightMode(nightMode);
    }

    @Override
    public boolean isNightMode() {
        return mPreferenceProvider.isNightMode();
    }

    @Override
    public Maybe<Boolean> clearDatabase() {
        return mDataRepository.clearDatabase();
    }
}
