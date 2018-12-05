package com.vaynefond.zhihudaily.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class AppToast {
    private static Toast sToast;
    private static WeakReference<Context> sContextRef;

    public static void initialize(@NonNull Context context) {
        sContextRef = new WeakReference<>(context.getApplicationContext());
    }

    public static void showLongText(CharSequence text) {
        canclePreviousToast();
        sToast = Toast.makeText(sContextRef.get(), text, Toast.LENGTH_LONG);
        sToast.show();
    }

    public static void showLongText(@StringRes int resId) {
        canclePreviousToast();
        sToast = Toast.makeText(sContextRef.get(), resId, Toast.LENGTH_LONG);
        sToast.show();
    }

    public static void showShortText(CharSequence text) {
        canclePreviousToast();
        sToast = Toast.makeText(sContextRef.get(), text, Toast.LENGTH_SHORT);
        sToast.show();
    }

    public static void showShortText(@StringRes int resId) {
        canclePreviousToast();
        sToast = Toast.makeText(sContextRef.get(), resId, Toast.LENGTH_SHORT);
        sToast.show();
    }

    private static void canclePreviousToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
