package com.vaynefond.zhihudaily.ui.story;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.app.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class StoryActivity extends BaseActivity implements StoryFragment.OnWindowAttributeCallback {
    private static final String ARG_STORY_LIST = "arg_story_list";
    private static final String ARG_CURRENT_INDEX = "arg_current_index";

    @BindView(R.id.story_pager)
    ViewPager mStoryPager;

    private ArrayList<Integer> mStoryIds;
    private int mCurrentIndex;

    public static void start(@NonNull Context context, ArrayList<Integer> storyIds, int currentIndex) {
        Intent intent = new Intent(context, StoryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(ARG_STORY_LIST, storyIds);
        bundle.putInt(ARG_CURRENT_INDEX, currentIndex);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
        setupViews();
    }

    private void initVars() {
        mStoryIds = getIntent().getIntegerArrayListExtra(ARG_STORY_LIST);
        mCurrentIndex = getIntent().getIntExtra(ARG_CURRENT_INDEX, -1);
        if (mCurrentIndex == -1 || mStoryIds == null) {
            finish();
        }
    }

    private void setupViews() {
        setStatusBarColor(Color.TRANSPARENT);
        setupStoryPager();
    }

    private void setupStoryPager() {
        FragmentStatePagerAdapter pagerAdapter = new StoryPagerAdapter(getSupportFragmentManager(), mStoryIds);
        mStoryPager.setAdapter(pagerAdapter);
        mStoryPager.setOffscreenPageLimit(1);
        mStoryPager.setCurrentItem(mCurrentIndex);

        mStoryPager.setOnApplyWindowInsetsListener((v, insets) -> {
            insets = mStoryPager.onApplyWindowInsets(insets);
            if (insets.isConsumed()) {
                return insets;
            }

            boolean consumed = false;
            for (int i = 0; i < mStoryPager.getChildCount(); i++) {
                mStoryPager.getChildAt(i).dispatchApplyWindowInsets(insets);
                if (insets.isConsumed()) {
                    consumed = true;
                }
            }

            return consumed ? insets.consumeSystemWindowInsets() : insets;
        });
    }

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_story;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void updateWindowAttribute(boolean immersive) {
        View decorView = getWindow().getDecorView();
        int uiOptions = decorView.getSystemUiVisibility();

        if ((immersive && isNightMode()) || (!immersive && !isNightMode())) {
            uiOptions &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            uiOptions |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

    private class StoryPagerAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Integer> mIds;

        StoryPagerAdapter(FragmentManager fm, ArrayList<Integer> storyIds) {
            super(fm);
            mIds = storyIds;
        }

        @Override
        public Fragment getItem(int position) {
            return StoryFragment.newInstance(mIds.get(position));
        }

        @Override
        public int getCount() {
            return mIds == null ? 0 : mIds.size();
        }
    }
}
