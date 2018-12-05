package com.vaynefond.zhihudaily.service;

import android.content.Context;
import android.os.Environment;

import com.vaynefond.zhihudaily.base.rx.observer.BaseObserver;
import com.vaynefond.zhihudaily.base.rx.observer.BaseSubscriber;
import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.model.LatestPaper;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.utils.AppContext;
import com.vaynefond.zhihudaily.utils.FileUtils;
import com.vaynefond.zhihudaily.utils.RxUtils;

import java.io.File;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class CacheManager {
    private final DataManager mDataManager;

    private File mCacheFile;
    private File mExternalCacheFile;
    private File mDataFile;
    private File mDatabaseFile;

    @Inject
    public CacheManager(DataManager dataManager) {
        mDataManager = dataManager;
        initFiles();
    }

    private void initFiles() {
        final Context context = AppContext.get();
        mCacheFile = FileUtils.getCacheDir(context);
        mDataFile = FileUtils.getFilesDir(context);
        mDatabaseFile = FileUtils.getDatabaseDir(context);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mExternalCacheFile = context.getExternalCacheDir();
        }
    }

    public void offlineDownload(BaseSubscriber subscriber) {
        mDataManager.loadLatestPaper()
                .compose(RxUtils.ioIoMaybe())
                .map(LatestPaper::getDate)
                .flatMap(mDataManager::loadStoriesBefore)
                .toObservable()
                .flatMap((Function<Paper, ObservableSource<Story>>) paper -> Observable.fromIterable(paper.getStories()))
                .map(Story::getId)
                .subscribe(new BaseObserver<Integer>() {
                    @Override
                    public void onNext(Integer storyId) {
                        Maybe.merge(mDataManager.loadStoryContent(storyId),
                                mDataManager.loadStoryExtra(storyId),
                                mDataManager.loadLongComments(storyId),
                                mDataManager.loadShortComments(storyId))
                                .compose(RxUtils.ioMainFlowable())
                                .subscribe(subscriber);
                    }
                });
    }

    public Maybe<Long> getCacheSize() {
        return Maybe.defer((Callable<MaybeSource<Long>>) () ->
                    Maybe.just(getAppDataSize()));
    }

    private long getAppDataSize() {
        return FileUtils.getFileSize(mCacheFile) +
                FileUtils.getFileSize(mDatabaseFile) +
                FileUtils.getFileSize(mExternalCacheFile) +
                FileUtils.getFileSize(mDataFile);
    }

    public Maybe<Boolean> clearCache() {
        return Maybe.defer((Callable<MaybeSource<Boolean>>) () -> {
            clearAppData();
            return Maybe.just(true);
        });
    }

    private void clearAppData() {
        FileUtils.deleteFile(mCacheFile);
        FileUtils.deleteFile(mDatabaseFile);
        FileUtils.deleteFile(mDataFile);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteFile(mExternalCacheFile);
        }
    }
}
