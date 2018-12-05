package com.vaynefond.zhihudaily.ui.home;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.adapter.TopPagerAdapter;
import com.vaynefond.zhihudaily.data.model.TopStory;
import com.vaynefond.zhihudaily.ui.story.StoryActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class SlidePagerDelegate {
    private Context mContext;

    private View mPagerLayout;
    private ViewPager mTopPager;
    private TopPagerAdapter mPagerAdapter;

    private static final int AUTO_SLIDE_INTERVAL = 5000;
    private final Handler mHandler;
    private Runnable mAutoSlideRunnable = new Runnable() {
        @Override
        public void run() {
            if (mTopPager.getAdapter() == null || mTopPager.getAdapter().getCount() == 0) {
                return;
            }

            int nextItem = (mTopPager.getCurrentItem() + 1) % mTopPager.getAdapter().getCount();
            mTopPager.setCurrentItem(nextItem, true);
        }
    };

    public SlidePagerDelegate(@NonNull Context context) {
        mContext = context;
        mHandler = new Handler(context.getMainLooper());
    }

    public void setup(@NonNull ViewGroup parent) {
        mPagerLayout = parent.findViewById(R.id.top_pager_layout);
        mTopPager = mPagerLayout.findViewById(R.id.top_story_pager);
        mPagerAdapter = new TopPagerAdapter(mContext);
        mPagerAdapter.setOnItemClickListener((view, position) -> {
            ArrayList<Integer> storyIds = mPagerAdapter.generateStoryIdList();
            StoryActivity.start(mContext, storyIds, position);
        });
        mTopPager.setAdapter(mPagerAdapter);
        mTopPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (parent instanceof SwipeRefreshLayout) {
                    parent.setEnabled(state != ViewPager.SCROLL_STATE_DRAGGING);
                }
            }
        });

        CirclePageIndicator pageIndicator = mPagerLayout.findViewById(R.id.top_story_pager_indicator);
        pageIndicator.setViewPager(mTopPager);
        pageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mHandler.removeCallbacks(mAutoSlideRunnable);
                mHandler.postDelayed(mAutoSlideRunnable, AUTO_SLIDE_INTERVAL);
            }
        });
    }

    public View getLayout() {
        return mPagerLayout;
    }

    public void notifyTopPaperUpdate(@NonNull List<TopStory> topStories) {
        mPagerAdapter.bindData(topStories);
        mPagerAdapter.notifyDataSetChanged();
    }

    private void startAutoSlideViewPager() {
        mHandler.removeCallbacks(mAutoSlideRunnable);
        mHandler.postDelayed(mAutoSlideRunnable, AUTO_SLIDE_INTERVAL);
    }

    private void stopAutoSlideViewPager() {
        mHandler.removeCallbacks(mAutoSlideRunnable);
    }

    public void startAutoSlide() {
        startAutoSlideViewPager();
    }

    public void stopAutoSlide() {
        stopAutoSlideViewPager();
    }
}
