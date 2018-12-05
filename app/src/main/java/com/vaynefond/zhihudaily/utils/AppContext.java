package com.vaynefond.zhihudaily.utils;

import android.content.Context;
import android.support.annotation.NonNull;

public class AppContext {
    private static AppContext INSTANCE;
    private final Context mContext;

    private AppContext(@NonNull Context context) {
        mContext = context.getApplicationContext();
    }

    public static void initialize(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new AppContext(context);
        }
    }

    public static Context get() {
        if (INSTANCE.mContext == null) {
            throw new IllegalArgumentException("AppContext has not initialized!");
        }

        return INSTANCE.mContext;
    }
}
