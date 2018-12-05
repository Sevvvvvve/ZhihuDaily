package com.vaynefond.zhihudaily.data.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_AUTO_OFFLINE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_BIG_FONT_MODE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_COMMENT_SHARE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_NIGHT_MODE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_NO_IMAGE_MODE;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_PUSH;
import static com.vaynefond.zhihudaily.data.preference.PrefConstants.PREF_KEY_VERSION_NUMBER;

@Singleton
public class AppPreferenceProvider implements PreferenceProvider {
    private final SharedPreferences mSharedPreferences;

    @Inject
    public AppPreferenceProvider(Context context, String prefName) {
        mSharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isAutoOffline() {
        return mSharedPreferences.getBoolean(PREF_KEY_AUTO_OFFLINE, true);
    }

    @Override
    public void setAutoOffline(boolean autoOffline) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_AUTO_OFFLINE, autoOffline).apply();
    }

    @Override
    public boolean isNoImageMode() {
        return mSharedPreferences.getBoolean(PREF_KEY_NO_IMAGE_MODE, false);
    }

    @Override
    public void setNoImageMode(boolean noImageMode) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_NO_IMAGE_MODE, noImageMode).apply();
    }

    @Override
    public boolean isBigFontMode() {
        return mSharedPreferences.getBoolean(PREF_KEY_BIG_FONT_MODE, false);
    }

    @Override
    public void setBigFontMode(boolean bigFont) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_BIG_FONT_MODE, bigFont).apply();
    }

    @Override
    public boolean isAutoPush() {
        return mSharedPreferences.getBoolean(PREF_KEY_PUSH, true);
    }

    @Override
    public void setAutoPush(boolean push) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_PUSH, push).apply();
    }

    @Override
    public boolean isCommentShare() {
        return mSharedPreferences.getBoolean(PREF_KEY_COMMENT_SHARE, true);
    }

    @Override
    public void setCommentShare(boolean commentShare) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_COMMENT_SHARE, commentShare).apply();
    }

    @Override
    public String getVersionNumber() {
        return mSharedPreferences.getString(PREF_KEY_VERSION_NUMBER, "2.6.6(795)");
    }

    @Override
    public void setVersionNumber(@NonNull String versionNumber) {
        mSharedPreferences.edit().putString(PREF_KEY_VERSION_NUMBER, versionNumber).apply();
    }

    @Override
    public void setNightMode(boolean nightMode) {
        mSharedPreferences.edit().putBoolean(PREF_KEY_NIGHT_MODE, nightMode).apply();
    }

    @Override
    public boolean isNightMode() {
        return mSharedPreferences.getBoolean(PREF_KEY_NIGHT_MODE, false);
    }
}
