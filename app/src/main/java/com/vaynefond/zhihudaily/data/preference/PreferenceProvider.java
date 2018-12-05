package com.vaynefond.zhihudaily.data.preference;

import android.support.annotation.NonNull;

public interface PreferenceProvider {
    boolean isAutoOffline();

    void setAutoOffline(boolean autoOffline);

    boolean isNoImageMode();

    void setNoImageMode(boolean noImageMode);

    boolean isBigFontMode();

    void setBigFontMode(boolean bigFont);

    boolean isAutoPush();

    void setAutoPush(boolean push);

    boolean isCommentShare();

    void setCommentShare(boolean commentShare);

    String getVersionNumber();

    void setVersionNumber(@NonNull String versionNumber);

    void setNightMode(boolean nightMode);

    boolean isNightMode();
}
