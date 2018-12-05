package com.vaynefond.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.image.ImageLoader;
import com.vaynefond.zhihudaily.data.model.TopStory;

import java.util.ArrayList;
import java.util.List;

public class TopPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<TopStory> mTopStories;

    private OnItemClickListener mOnItemClickListener;

    public TopPagerAdapter(@NonNull Context context) {
        mContext = context;
    }

    public void bindData(@NonNull List<TopStory> topStories) {
        mTopStories = topStories;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.top_page_item, container, false);
        bindViewData(itemView, position);
        container.addView(itemView);

        return itemView;
    }

    private void bindViewData(View itemView, int position) {
        final TopStory topStory = mTopStories.get(position);

        ImageView topStoryImage = itemView.findViewById(R.id.top_story_image);
        TextView topStoryTitle = itemView.findViewById(R.id.top_story_title);
        ImageLoader.loadImage(topStoryImage, topStory.getImage());
        topStoryTitle.setText(topStory.getTitle());

        itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(itemView, position);
            }
        });
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public int getCount() {
        return mTopStories == null ? 0 : mTopStories.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public ArrayList<Integer> generateStoryIdList() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (TopStory topStory : mTopStories) {
            ids.add(topStory.getId());
        }

        return ids;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull View view, int position);
    }
}
