package com.vaynefond.zhihudaily.data.repository;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.Version;
import com.vaynefond.zhihudaily.data.repository.local.LocalDataRepository;
import com.vaynefond.zhihudaily.data.repository.remote.RemoteDataRepository;
import com.vaynefond.zhihudaily.di.Local;
import com.vaynefond.zhihudaily.di.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class AppDataRepository implements DataRepository {
    private final LocalDataRepository mLocalDataRepository;
    private final RemoteDataRepository mRemoteDataRepository;

    @Inject
    public AppDataRepository(@Local LocalDataRepository localDataRepository,
                      @Remote RemoteDataRepository remoteDataRepository) {
        mLocalDataRepository = localDataRepository;
        mRemoteDataRepository = remoteDataRepository;
    }

    @Override
    public Maybe<Version> loadLatestVersion() {
        return mRemoteDataRepository.loadLatestVersion();
    }

    @Override
    public Maybe<LatestPaper> loadLatestPaper() {
        Maybe<LatestPaper> local = mLocalDataRepository.loadLatestPaper();
        Maybe<LatestPaper> remote = mRemoteDataRepository.loadLatestPaper()
                .doOnSuccess(latestPaper -> {
                    appendDateToStories(latestPaper.getDate(), latestPaper.getStories());
                    mLocalDataRepository.cacheLatestPaper(latestPaper);
                })
                .onErrorResumeNext(local);

        return Maybe.concat(remote, local)
                .filter(latestPaper -> latestPaper != null)
                .firstElement();
    }

    @Override
    public Maybe<Paper> loadStoriesBefore(String date) {
        Maybe<Paper> local = mLocalDataRepository.loadStoriesBefore(date);
        Maybe<Paper> remote = mRemoteDataRepository.loadStoriesBefore(date)
                .doOnSuccess(paper -> {
                    appendDateToStories(paper.getDate(), paper.getStories());
                    mLocalDataRepository.cacheStories(paper.getStories());
                });

        return Maybe.concat(local, remote)
                .filter(paper -> paper != null)
                .firstElement();
    }

    @Override
    public Maybe<StoryContent> loadStoryContent(int storyId) {
        Maybe<StoryContent> local = mLocalDataRepository.loadStoryContent(storyId);
        Maybe<StoryContent> remote = mRemoteDataRepository.loadStoryContent(storyId)
                .doOnSuccess(mLocalDataRepository::cacheStoryContent);

        return Maybe.concat(local, remote)
                .filter(storyContent -> storyContent != null)
                .firstElement();
    }

    @Override
    public Maybe<StoryExtra> loadStoryExtra(int storyId) {
        Maybe<StoryExtra> local = mLocalDataRepository.loadStoryExtra(storyId);
        Maybe<StoryExtra> remote = mRemoteDataRepository.loadStoryExtra(storyId)
                .doOnSuccess(mLocalDataRepository::cacheStoryExtra);

        return Maybe.concat(remote, local)
                .filter(storyExtra -> storyExtra != null)
                .firstElement();
    }

    @Override
    public Maybe<Theme> loadTheme(int id) {
        return Maybe.empty();
    }

    @Override
    public Maybe<List<Theme>> loadThemes() {
        return mRemoteDataRepository.loadThemes()
                .doOnSuccess(mLocalDataRepository::cacheThemes);
    }

    @Override
    public Maybe<List<Comment>> loadLongComments(int storyId) {
        return mRemoteDataRepository.loadLongComments(storyId);
    }

    @Override
    public Maybe<List<Comment>> loadShortComments(int storyId) {
        return mRemoteDataRepository.loadShortComments(storyId);
    }

    @Override
    public Maybe<List<Story>> loadFavoriteStories() {
        return mLocalDataRepository.loadFavoriteStories();
    }

    @Override
    public Maybe<Story> loadStory(int storyId) {
        return mLocalDataRepository.loadStory(storyId);
    }

    @Override
    public Maybe<Boolean> favoriteStory(int storyId, boolean favorite) {
        return mLocalDataRepository.favoriteStory(storyId, favorite);
    }

    @Override
    public Maybe<Boolean> clearDatabase() {
        return mLocalDataRepository.clearDatabase();
    }

    private void appendDateToStories(String date, List<Story> stories) {
        for (Story story : stories) {
            story.setDate(date);
        }
    }
}
