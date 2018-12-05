package com.vaynefond.zhihudaily.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public class ResourceUtils {
    public static int getThemeResourceId(@NonNull Context context, int resId) {
        TypedValue value = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(resId, value, true);
        return value.resourceId;
    }

    public static int getColor(@NonNull Context context, int resId) {
        final int resourceId = getThemeResourceId(context, resId);

        if (VersionUtils.isAndroidMOrHigher()) {
            return context.getColor(resourceId);
        } else {
            return context.getResources().getColor(resourceId, context.getTheme());
        }
    }
}
