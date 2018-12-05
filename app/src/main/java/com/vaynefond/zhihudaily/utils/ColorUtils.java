package com.vaynefond.zhihudaily.utils;

import android.graphics.Color;
import android.support.annotation.ColorInt;

public class ColorUtils {
    public static @ColorInt int alpha(int color, float factor) {
        int alpha = (int) (Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }
}
