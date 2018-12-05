package com.vaynefond.zhihudaily.ui.comment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.adapter.CommentAdapter;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpFragment;
import com.vaynefond.zhihudaily.data.model.Comment;
import com.vaynefond.zhihudaily.di.ActivityScoped;
import com.vaynefond.zhihudaily.utils.ViewUtils;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;

@ActivityScoped
public class CommentFragment extends BaseMvpFragment<CommentPresenter, CommentContract.View>
        implements CommentContract.View {
    private static final String ARG_STORY_ID = "arg_story_id";
    private static final int INVALID_ID = -1;
    private int mStoryId = INVALID_ID;

    @BindView(R.id.comment_recyclerview)
    RecyclerView mCommentRv;

    private CommentAdapter mCommentAdapter;

    private int mScrollPosition;
    private boolean mScrollToTop;

    @Inject
    public CommentFragment() {
    }

    public static CommentFragment newInstance(int storyId) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStoryId = getArguments().getInt(ARG_STORY_ID, INVALID_ID);
        }

        if (mStoryId == INVALID_ID) {
            requireActivity().onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadCommentExtra(mStoryId);
        mPresenter.loadLongComments(mStoryId);
        mPresenter.loadShortComments(mStoryId);
    }

    @Override
    protected int fragmentLayoutId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void setupViews(@NonNull View root) {
        mCommentRv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        mCommentRv.addItemDecoration(obtainDividerItemDecoration());
        mCommentAdapter = new CommentAdapter(requireContext());
        mCommentAdapter.setOnExpandListener((expanded, position) -> {
            mScrollPosition = position;
            if (expanded) {
                scrollItemToTop(mCommentRv, position);
            } else {
                mCommentRv.smoothScrollToPosition(position);
            }
        });
        mCommentRv.setAdapter(mCommentAdapter);

        mCommentRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mScrollToTop) {
                    mScrollToTop = false;
                    int firstVisibleItemPosition = ViewUtils.findFirstVisibleItemPosition(recyclerView);
                    int itemOffset = mScrollPosition - firstVisibleItemPosition;
                    if (itemOffset >= 0 && itemOffset < recyclerView.getChildCount()) {
                        recyclerView.smoothScrollBy(0, recyclerView.getChildAt(itemOffset).getTop());
                    }
                }
            }
        });
    }

    private void scrollItemToTop(RecyclerView recyclerView, int position) {
        int firstVisibleItemPosition = ViewUtils.findFirstVisibleItemPosition(recyclerView);
        int lastVisibleItemPosition = ViewUtils.findLastVisibleItemPosition(recyclerView);

        if (position <= firstVisibleItemPosition) {
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastVisibleItemPosition) {
            recyclerView.smoothScrollBy(0, recyclerView.getChildAt(
                    position - firstVisibleItemPosition).getTop());
        } else {
            recyclerView.smoothScrollToPosition(position);
            mScrollToTop = true;
        }
    }

    @NonNull
    private DividerItemDecoration obtainDividerItemDecoration() {
        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        decoration.setDrawable(Objects.requireNonNull(requireContext().getDrawable(R.drawable.common_divider)));
        return decoration;
    }

    @Override
    public void showCommentsExtra(int totalCommentCount) {
        if (requireActivity() instanceof OnToolbarListener) {
            ((OnToolbarListener) requireActivity())
                    .updateCommentTitle(totalCommentCount);
        }
    }

    @Override
    public void showLongComments(@NonNull List<Comment> comments) {
        mCommentAdapter.bindLongComments(comments);
        mCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showShortComments(@NonNull List<Comment> comments) {
        mCommentAdapter.bindShortComments(comments);
        mCommentAdapter.notifyDataSetChanged();
    }

    public interface OnToolbarListener {
        void updateCommentTitle(int totalComments);
    }
}
