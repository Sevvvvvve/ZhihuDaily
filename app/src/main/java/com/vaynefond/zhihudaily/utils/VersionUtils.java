package com.vaynefond.zhihudaily.utils;

import android.os.Build;

public class VersionUtils {
    public static boolean isAndroidLOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isAndroidMOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean isAndroidNOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean isAndroidOOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }


    public static boolean isAndroidPOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }
}
