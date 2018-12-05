package com.vaynefond.zhihudaily.base.widget;

import android.support.annotation.NonNull;
import android.view.View;

class ViewInfo {
    View view;

    public static ViewInfo create(@NonNull View view) {
        final ViewInfo info = new ViewInfo();
        info.view = view;
        return info;
    }
}
