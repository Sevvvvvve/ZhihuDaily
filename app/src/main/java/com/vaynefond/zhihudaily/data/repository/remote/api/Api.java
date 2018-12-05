package com.vaynefond.zhihudaily.data.repository.remote.api;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.Version;

import java.util.List;

import io.reactivex.Maybe;

public interface Api {
    Maybe<Version> getLatestVersion();

    Maybe<LatestPaper> getLatestPaper();

    Maybe<Paper> getStoriesBefore(String date);

    Maybe<StoryContent> getStoryContent(int storyId);

    Maybe<StoryExtra> getStoryExtra(int storyId);

    Maybe<Theme> getTheme(int themeId);

    Maybe<List<Theme>> getThemes();

    Maybe<List<Comment>> getLongComments(int storyId);

    Maybe<List<Comment>> getShortComments(int storyId);
}
