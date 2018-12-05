package com.vaynefond.zhihudaily.app;

import com.vaynefond.zhihudaily.data.DataManager;
import com.vaynefond.zhihudaily.data.repository.DataRepository;

public interface ZhihuApp {
    DataManager getDataManager();

    boolean isNightMode();

    void setNightMode(boolean nightMode);
}
