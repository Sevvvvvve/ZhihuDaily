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
import com.vaynefond.zhihudaily.base.image.ImageLoader;
import com.vaynefond.zhihudaily.data.model.Story;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class FavoritesAdapter extends BaseAdapter {
    private List<Story> mStories;

    public FavoritesAdapter(@NonNull Context context) {
        super(context);
    }

    public void bindData(@NonNull List<Story> stories) {
        mStories = stories;
        Collections.sort(mStories, (o1, o2) -> o1.getFavoriteDate() > o2.getFavoriteDate() ? 1 : -1);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteHolder(getLayoutInflater()
                .inflate(R.layout.story_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        FavoriteHolder favoriteHolder = (FavoriteHolder) holder;
        final Story story = mStories.get(position);
        favoriteHolder.mTitle.setText(story.getTitle());
        ImageLoader.loadImage(favoriteHolder.mImage, story.getImages().get(0));
        favoriteHolder.mMultipic.setVisibility(
                story.isMultipic() ? View.VISIBLE : View.GONE);
        initItemViewListener(holder.itemView, position);
    }

    public ArrayList<Integer> generateStoryIdList() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Story story : mStories) {
            ids.add(story.getId());
        }

        return ids;
    }

    @Override
    public int getItemCount() {
        return mStories == null ? 0 : mStories.size();
    }

    static class FavoriteHolder extends BaseViewHolder {
        @BindView(R.id.story_title)
        TextView mTitle;

        @BindView(R.id.story_image)
        ImageView mImage;

        @BindView(R.id.story_multipic)
        ImageView mMultipic;

        FavoriteHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
