package com.vaynefond.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.adapter.BaseAdapter;
import com.vaynefond.zhihudaily.base.adapter.BaseViewHolder;
import com.vaynefond.zhihudaily.data.model.Theme;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ThemeAdapter extends BaseAdapter {
    private List<Theme> mThemes;

    public ThemeAdapter(@NonNull Context context) {
        super(context);
    }

    public void bindData(List<Theme> themes) {
        mThemes = themes;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ThemeViewHolder(getLayoutInflater()
                .inflate(R.layout.navigation_theme_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ThemeViewHolder themeHolder = (ThemeViewHolder) holder;
        Theme theme = mThemes.get(position);
        themeHolder.mLogo.setImageResource(theme.getLogo());
        themeHolder.mTitle.setText(theme.getName());
        if (theme.isSubscribed()) {
            themeHolder.mSubscribeButton.setImageResource(R.drawable.home_arrow);
        } else {
            themeHolder.mSubscribeButton.setVisibility(View.GONE);
        }
        initItemViewListener(themeHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mThemes == null ? 0 : mThemes.size();
    }

    class ThemeViewHolder extends BaseViewHolder {
        @BindView(R.id.nav_item_logo)
        ImageView mLogo;

        @BindView(R.id.nav_item_title)
        TextView mTitle;

        @BindView(R.id.nav_item_subscribe_button)
        ImageView mSubscribeButton;

        ThemeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
