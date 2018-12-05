package com.vaynefond.zhihudaily.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.adapter.StoryAdapter;
import com.vaynefond.zhihudaily.base.app.BaseActivity;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpFragment;
import com.vaynefond.zhihudaily.base.widget.BaseRecyclerView;
import com.vaynefond.zhihudaily.base.widget.SimpleLoadMoreView;
import com.vaynefond.zhihudaily.data.model.Paper;
import com.vaynefond.zhihudaily.data.model.TopStory;
import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.ui.story.StoryActivity;
import com.vaynefond.zhihudaily.utils.ColorUtils;
import com.vaynefond.zhihudaily.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

@ActivityScoped
public class HomeFragment extends BaseMvpFragment<HomePresenter, HomeContract.View>
        implements HomeContract.View, BaseActivity.OnThemeChangedListener {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.story_recyclerview)
    BaseRecyclerView mStoryRecyclerView;

    private SimpleLoadMoreView mLoadMoreView;
    private StoryAdapter mStoryAdapter;
    private String mLoadedDate;

    private SlidePagerDelegate mSlidePagerDelegate;

    private int mToolbarBottomY;
    private String mTitle;
    private float mOffsetFactor;

    @Inject
    public HomeFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerThemeChangedListener(this);
    }

    @Override
    protected int fragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setupViews(@NonNull View root) {
        setupRefreshLayout();
        setupAppBarLayout();
        setupStoryRecyclerView();
        setupViewPager();
    }

    private void setupRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                ResourceUtils.getThemeResourceId(requireContext(), R.attr.colorPrimary));
        mRefreshLayout.setOnRefreshListener(() -> {
            mRefreshLayout.setRefreshing(true);
            mPresenter.loadLatestPaper();
        });
    }

    private void setupAppBarLayout() {
        mToolbarBottomY = getResources().getDimensionPixelSize(R.dimen.action_bar_size);
        updateToolBarColor(Color.TRANSPARENT);
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            mRefreshLayout.setEnabled(verticalOffset >= 0);

            mOffsetFactor = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
            int appBarColor = ColorUtils.alpha(getThemeColor(R.attr.colorPrimary), mOffsetFactor);
            updateToolBarColor(appBarColor);
        });
    }

    private void updateToolBarColor(@ColorInt int color) {
        if (mOnFragmentInteractionListener != null) {
            mOnFragmentInteractionListener.updateToolBarColor(color);
        }
    }

    private void updateToolBarTitle(String title) {
        if (mOnFragmentInteractionListener != null) {
            mOnFragmentInteractionListener.updateToolBarTitle(title);
        }
    }

    private void setupStoryRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        mStoryRecyclerView.setLayoutManager(layoutManager);
        mStoryAdapter = new StoryAdapter(requireContext());
        mStoryAdapter.setOnItemClickListener((view, position) -> {
            ArrayList<Integer> storyIds = mStoryAdapter.generateStoryIdList();
            StoryActivity.start(requireContext(), storyIds, position);
        });
        mStoryRecyclerView.setAdapter(mStoryAdapter);

        addScrollListener();
        addStoryDecoration();
        mLoadMoreView = new SimpleLoadMoreView(requireContext());
        mLoadMoreView.setOnRetryListener(this::loadMorePaper);
        mStoryRecyclerView.setOnLoadMoreListener(this::loadMorePaper);
    }

    private void setupViewPager() {
        mSlidePagerDelegate = new SlidePagerDelegate(requireContext());
        mSlidePagerDelegate.setup(mRefreshLayout);
    }

    private void addScrollListener() {
        mStoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!(requireContext() instanceof OnFragmentInteractionListener) ||
                        recyclerView.getLayoutManager() == null) {
                    return;
                }

                int position = findFirstVisiblePositionUnderToolbar(recyclerView);
                StoryInfo info = mStoryAdapter.getStoryInfoByPosition(position);
                mTitle = info != null ? info.getDate() : requireContext().getString(R.string.home_today);
                if (mOnFragmentInteractionListener != null) {
                    mOnFragmentInteractionListener.updateToolBarTitle(mTitle);
                }
            }
        });
    }

    private int findFirstVisiblePositionUnderToolbar(@NonNull RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return NO_POSITION;
        }

        for (int i = 0; i < layoutManager.getChildCount(); i++) {
            final View view = layoutManager.getChildAt(i);
            if (view != null && view.getBottom() >= mToolbarBottomY) {
                return recyclerView.getChildAdapterPosition(view);
            }
        }

        return NO_POSITION;
    }

    private void addStoryDecoration() {
        StoryDecoration decoration = new StoryDecoration(requireContext(),
                position -> mStoryAdapter.getStoryInfoByPosition(position));
        mStoryRecyclerView.addItemDecoration(decoration);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadLatestPaper();
    }

    private void loadMorePaper() {
        if (mLoadedDate != null) {
            mLoadMoreView.onLoading();
            mPresenter.loadStoriesByDate(mLoadedDate);
        }
    }

    @Override
    public void showTopPaper(@NonNull List<TopStory> topStories) {
        mSlidePagerDelegate.notifyTopPaperUpdate(topStories);
        mSlidePagerDelegate.startAutoSlide();
    }

    @Override
    public void showPaper(@NonNull Paper paper) {
        updateLoadedDate(paper.getDate());
        mStoryAdapter.addPaper(paper);
        notifyStoryChanged();
        updateRefreshLoadingStatus();
    }

    private void updateLoadedDate(String date) {
        mLoadedDate = date;
    }

    private void updateRefreshLoadingStatus() {
        mRefreshLayout.setRefreshing(false);
        mLoadMoreView.onLoadComplete();
    }

    private void notifyStoryChanged() {
        if (mStoryRecyclerView.getAdapter() != null) {
            mStoryRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onThemeChanged() {
        if (isVisible()) {
            updateToolBarColor(ColorUtils.alpha(getThemeColor(R.attr.colorPrimary), mOffsetFactor));
        }

        int backgroundColor = getThemeColor(R.attr.activityBackgroundGray);
        mStoryRecyclerView.setBackgroundColor(backgroundColor);

        for (int i = 0; i < mStoryRecyclerView.getChildCount(); i++) {
            final View child = mStoryRecyclerView.getChildAt(i);

            if (child instanceof CardView) {
                int cardItemBackground = getThemeColor(R.attr.cardItemBackground);
                ((CardView) child).setCardBackgroundColor(cardItemBackground);

                TextView title = child.findViewById(R.id.story_title);
                if (title != null) {
                    title.setTextColor(getThemeColor(R.attr.itemTitleUnreadTextColor));
                }
            }
        }
        invalidateStoryRecyclerView(mStoryRecyclerView);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            updateToolBarColor(ColorUtils.alpha(getThemeColor(R.attr.colorPrimary), mOffsetFactor));
            updateToolBarTitle(mTitle);
        }
    }

    @Override
    public void onDestroyView() {
        mSlidePagerDelegate.stopAutoSlide();
        super.onDestroyView();
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        super.onError(errorCode, errorMsg);
        updateRefreshLoadingStatus();
    }
}
