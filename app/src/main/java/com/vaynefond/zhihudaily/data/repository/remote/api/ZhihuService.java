package com.vaynefond.zhihudaily.data.repository.remote.api;

import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.LongComments;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.ShortComments;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.Themes;
import com.vaynefond.zhihudaily.data.model.Version;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ZhihuService {
    @GET("4/version/android/2.6.6")
    Maybe<Version> getLatestVersion();

    @GET("4/news/latest")
    Maybe<LatestPaper> getLatestPaper();

    @GET("4/news/{id}")
    Maybe<StoryContent> getStoryContent(@Path("id") int storyId);

    @GET("4/news/before/{date}")
    Maybe<Paper> getStoriesBefore(@Path("date") String date);

    @GET("4/story-extra/{id}")
    Maybe<StoryExtra> getStoryExtra(@Path("id") int storyId);

    @GET("4/theme/{id}")
    Maybe<Theme> getTheme(@Path("id") int themeId);

    @GET("4/themes")
    Maybe<Themes> getThemes();

    @GET("4/story/{id}/long-comments")
    Maybe<LongComments> getLongComments(@Path("id") int storyId);

    @GET("4/story/{id}/short-comments")
    Maybe<ShortComments> getShortComments(@Path("id") int storyId);
}
