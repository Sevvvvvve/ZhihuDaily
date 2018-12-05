package com.vaynefond.zhihudaily.data.repository;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.TopStory;
import com.vaynefond.zhihudaily.data.model.Version;

import java.util.List;

import io.reactivex.Maybe;

public interface DataRepository {
    Maybe<Version> loadLatestVersion();

    Maybe<LatestPaper> loadLatestPaper();

    Maybe<Paper> loadStoriesBefore(String date);

    Maybe<StoryContent> loadStoryContent(int storyId);

    Maybe<StoryExtra> loadStoryExtra(int storyId);

    Maybe<Theme> loadTheme(int id);

    Maybe<List<Theme>> loadThemes();

    Maybe<List<Comment>> loadLongComments(int storyId);

    Maybe<List<Comment>> loadShortComments(int storyId);

    Maybe<List<Story>> loadFavoriteStories();

    Maybe<Story> loadStory(int storyId);

    Maybe<Boolean> favoriteStory(int storyId, boolean favorite);

    Maybe<Boolean> clearDatabase();
}
