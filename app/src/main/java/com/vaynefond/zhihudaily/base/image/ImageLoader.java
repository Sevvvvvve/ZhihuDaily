package com.vaynefond.zhihudaily.base.image;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vaynefond.zhihudaily.R;

public class ImageLoader {
    public static void loadImage(@NonNull ImageView view, String path) {
        Picasso.get().load(path)
                .placeholder(R.drawable.image_top_default)
                .fit()
                .centerCrop()
                .into(view);
    }
}
