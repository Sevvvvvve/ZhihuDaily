package com.vaynefond.zhihudaily.base.widget;

public interface LoadMoreView {
    void onLoading();

    void onLoadComplete();

    void onLoadError(String message);

    void onLoadEnd();
}
