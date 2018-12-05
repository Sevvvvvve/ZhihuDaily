package com.vaynefond.zhihudaily.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class ComponentUtils {
    public static <T extends Activity> void startActivity(@NonNull Context context, @NonNull Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public static <T extends Service> void startService(@NonNull Context context, @NonNull Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startService(intent);
    }

    public static <T extends Service> void stopService(@NonNull Context context, @NonNull Class<T> clazz) {
        Intent intent = new Intent(context, clazz);
        context.stopService(intent);
    }
}
