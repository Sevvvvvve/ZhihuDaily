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
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.ui.home.StoryInfo;
import com.vaynefond.zhihudaily.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class StoryAdapter extends BaseAdapter {
    private static final String DATE_FORMAT = "MM月dd日 EEEE";

    private List<Paper> mPapers = new ArrayList<>();

    public StoryAdapter(@NonNull Context context) {
        super(context);
    }

    public void addPaper(@NonNull Paper paper) {
        String date = paper.getDate();
        for (Paper p : mPapers) {
            if (p.getDate().equals(date)) {
                return;
            }
        }

        mPapers.add(paper);
        Collections.sort(mPapers, (p1, p2) -> p2.getDate().compareTo(p1.getDate()));
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StoryHolder(getLayoutInflater()
                .inflate(R.layout.story_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        StoryHolder storyHolder = (StoryHolder) holder;
        final Story story = getStoryByPosition(position);
        storyHolder.mTitle.setText(story.getTitle());
        ImageLoader.loadImage(storyHolder.mImage, story.getImages().get(0));
        storyHolder.mMultipic.setVisibility(
                story.isMultipic() ? View.VISIBLE : View.GONE);
        initItemViewListener(holder.itemView, position);
    }

    private Story getStoryByPosition(int position) {
        int paperIndex = 0;
        int storyIndex = 0;

        for (int i = 0, storyCount = 0; i < mPapers.size(); i++) {
            int nextSize = mPapers.get(i).getStories().size();
            if (position < storyCount + nextSize) {
                paperIndex = i;
                storyIndex = position - storyCount;
                break;
            }
            storyCount += nextSize;
        }

        return mPapers.get(paperIndex).getStories().get(storyIndex);
    }

    public ArrayList<Integer> generateStoryIdList() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Paper paper : mPapers) {
            for (Story story : paper.getStories()) {
                ids.add(story.getId());
            }
        }

        return ids;
    }

    public StoryInfo getStoryInfoByPosition(int position) {
        if (mPapers.size() == 0) {
            return null;
        }

        int paperIndex = -1, storyIndex = -1;
        for (int i = 0, storyCount = 0; i < mPapers.size(); i++) {
            int nextSize = mPapers.get(i).getStories().size();
            if (position < storyCount + nextSize) {
                paperIndex = i;
                storyIndex = position - storyCount;
                break;
            }
            storyCount += nextSize;
        }

        if (paperIndex == -1 || storyIndex == -1) {
            return null;
        }

        Paper paper = mPapers.get(paperIndex);
        String date = DateUtils.formatDate(paper.getDate(), DATE_FORMAT);
        if (paperIndex == 0) {
            date = getContext().getResources().getString(R.string.home_today);
        }
        return new StoryInfo(paperIndex, date, storyIndex, paper.getStories().size());
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Paper paper : mPapers) {
            count += paper.getStories().size();
        }
        return count;
    }

    static class StoryHolder extends BaseViewHolder {
        @BindView(R.id.story_title)
        TextView mTitle;

        @BindView(R.id.story_image)
        ImageView mImage;

        @BindView(R.id.story_multipic)
        ImageView mMultipic;

        StoryHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
