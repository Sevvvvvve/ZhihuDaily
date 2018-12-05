package com.vaynefond.zhihudaily.ui.favorite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.adapter.FavoritesAdapter;
import com.vaynefond.zhihudaily.base.app.BaseActivity;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpFragment;
import com.vaynefond.zhihudaily.base.widget.BaseRecyclerView;
import com.vaynefond.zhihudaily.data.model.Story;
import com.vaynefond.zhihudaily.ui.story.StoryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;

public class FavoriteFragment extends BaseMvpFragment<FavoritePresenter, FavoriteContract.View>
        implements FavoriteContract.View, BaseActivity.OnThemeChangedListener {
    @BindView(R.id.favorite_recyclerview)
    BaseRecyclerView mFavoriteRv;

    private FavoritesAdapter mFavoritesAdapter;

    @Inject
    public FavoriteFragment() {
    }

    @Override
    protected int fragmentLayoutId() {
        return R.layout.fragment_favorite;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerThemeChangedListener(this);
    }

    @Override
    protected void setupViews(@NonNull View root) {
        setupFavoriteRecyclerView();
    }

    private void setupFavoriteRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        mFavoriteRv.setLayoutManager(layoutManager);
        mFavoriteRv.addItemDecoration(obtainDividerItemDecoration());

        mFavoritesAdapter = new FavoritesAdapter(requireContext());
        mFavoritesAdapter.setOnItemClickListener((view, position) -> {
            ArrayList<Integer> storyIds = mFavoritesAdapter.generateStoryIdList();
            StoryActivity.start(requireContext(), storyIds, position);
        });
        mFavoriteRv.setAdapter(mFavoritesAdapter);
    }

    @NonNull
    private DividerItemDecoration obtainDividerItemDecoration() {
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(requireContext().getDrawable(R.drawable.common_divider)));
        return decoration;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mOnFragmentInteractionListener != null) {
            mOnFragmentInteractionListener.updateToolBarColor(getThemeColor(R.attr.colorPrimary));
            mOnFragmentInteractionListener.updateToolBarTitle(getString(R.string.favorite));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFavoriteStories();
    }

    @Override
    public void showFavoriteStories(List<Story> stories) {
        mFavoritesAdapter.bindData(stories);
        if (mFavoriteRv.getAdapter() != null) {
            mFavoriteRv.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onThemeChanged() {
        if (isVisible()) {
            updateToolbarColor();
        }

        int backgroundColor = getThemeColor(R.attr.activityBackgroundGray);
        mFavoriteRv.setBackgroundColor(backgroundColor);

        for (int i = 0; i < mFavoriteRv.getChildCount(); i++) {
            final View child = mFavoriteRv.getChildAt(i);

            if (child instanceof CardView) {
                int cardItemBackground = getThemeColor(R.attr.cardItemBackground);
                ((CardView) child).setCardBackgroundColor(cardItemBackground);

                TextView title = child.findViewById(R.id.story_title);
                if (title != null) {
                    title.setTextColor(getThemeColor(R.attr.itemTitleUnreadTextColor));
                }
            }
        }
        invalidateStoryRecyclerView(mFavoriteRv);
    }

    private void updateToolbarColor() {
        if (mOnFragmentInteractionListener != null) {
            mOnFragmentInteractionListener.updateToolBarColor(getThemeColor(R.attr.colorPrimary));
        }
    }
}
