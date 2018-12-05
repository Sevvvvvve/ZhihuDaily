package com.vaynefond.zhihudaily.data.repository.remote.api;

import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.data.model.Version;
import com.vaynefond.zhihudaily.utils.AppContext;
import com.vaynefond.zhihudaily.utils.NetUtils;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Maybe;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService extends RetrofitService<ZhihuService> implements Api {
    private static final String BASE_URL = "https://news-at.zhihu.com/api/";
    private static final int DEFAULT_TIMEOUT = 10;

    @Inject
    public ApiService() {
    }

    @Override
    protected String baseUrl() {
        return BASE_URL;
    }

    @Override
    protected OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    if (NetUtils.isNetworkConnected(AppContext.get())) {
                        return chain.proceed(chain.request());
                    } else {
                        throw new UnknownHostException();
                    }
                })
                .build();
    }

    @Override
    protected Converter.Factory createConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Override
    protected CallAdapter.Factory createCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Override
    public Maybe<Version> getLatestVersion() {
        return mService.getLatestVersion();
    }

    @Override
    public Maybe<LatestPaper> getLatestPaper() {
        return mService.getLatestPaper();
    }

    @Override
    public Maybe<Paper> getStoriesBefore(String date) {
        return mService.getStoriesBefore(date);
    }

    @Override
    public Maybe<StoryContent> getStoryContent(int storyId) {
        return mService.getStoryContent(storyId);
    }

    @Override
    public Maybe<StoryExtra> getStoryExtra(int storyId) {
        return mService.getStoryExtra(storyId);
    }

    @Override
    public Maybe<Theme> getTheme(int themeId) {
        return mService.getTheme(themeId);
    }

    @Override
    public Maybe<List<Theme>> getThemes() {
        return mService.getThemes().flatMap(themes -> Maybe.just(themes.getThemes()));
    }

    @Override
    public Maybe<List<Comment>> getLongComments(int storyId) {
        return mService.getLongComments(storyId).flatMap(longComments -> Maybe.just(longComments.getComments()));
    }

    @Override
    public Maybe<List<Comment>> getShortComments(int storyId) {
        return mService.getShortComments(storyId).flatMap(shortComments -> Maybe.just(shortComments.getComments()));
    }


}
