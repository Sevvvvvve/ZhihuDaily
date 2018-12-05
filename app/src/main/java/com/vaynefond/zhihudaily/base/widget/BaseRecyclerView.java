package com.vaynefond.zhihudaily.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class BaseRecyclerView extends RecyclerView {
    private Adapter mAdapter;
    private boolean mIsObserverRegistered;
    private WrapDataObserver mDataObserver = new WrapDataObserver();

    private View mEmptyView;
    private FrameLayout mEmptyViewContainer;

    private SparseArray<ViewInfo> mHeaderViewInfos = new SparseArray<>();
    private SparseArray<ViewInfo> mFooterViewInfos = new SparseArray<>();

    private OnLoadMoreListener mOnLoadMoreListener;
    private FrameLayout mLoadMoreContainer;
    private LoadMoreView mLoadMoreView;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public BaseRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        initDecorationViewContainer();
    }

    private void initDecorationViewContainer() {
        mEmptyViewContainer = new FrameLayout(getContext());
        mEmptyViewContainer.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mLoadMoreContainer = new FrameLayout(getContext());
        mLoadMoreContainer.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(
                    new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == getAdapter().getItemCount() - 1) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
        addLoadMoreListener();
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        unregisterDataObserver();
        mAdapter = wrapAdapterInternal(adapter, mEmptyViewContainer,
                mHeaderViewInfos, mFooterViewInfos, mLoadMoreContainer);
        super.setAdapter(mAdapter);
        registerDataObserver();
        dispatchDataObserverOnChanged();
    }

    private Adapter wrapAdapterInternal(Adapter adapter, FrameLayout emptyViewContainer,
                                        SparseArray<ViewInfo> headerViewInfos, SparseArray<ViewInfo> footerViewInfos,
                                        FrameLayout loadMoreContainer) {
        return new HeaderRecyclerViewAdapter(adapter, emptyViewContainer,
                headerViewInfos, footerViewInfos, loadMoreContainer);
    }

    private void registerDataObserver() {
        if (mAdapter != null) {
            mAdapter.registerAdapterDataObserver(mDataObserver);
            mIsObserverRegistered = true;
        }
    }

    private void unregisterDataObserver() {
        if (mAdapter != null && mIsObserverRegistered) {
            mAdapter.unregisterAdapterDataObserver(mDataObserver);
            mIsObserverRegistered = false;
        }
    }

    private void dispatchDataObserverOnChanged() {
        if (mDataObserver != null) {
            mDataObserver.onChanged();
        }
    }

    public void setEmptyView(@NonNull View view) {
        if (mEmptyView != null) {
            mEmptyViewContainer.removeView(mEmptyView);
        }
        mEmptyView = view;
        mEmptyViewContainer.addView(mEmptyView);
    }

    public void addHeaderView(@NonNull View view) {
        final ViewInfo info = ViewInfo.create(view);
        mHeaderViewInfos.put(AdapterType.TYPE_HEADER_BASE + mHeaderViewInfos.size(), info);
        wrapAdapter();
    }

    public int getHeaderViewsCount() {
        return mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(@NonNull View view) {
        if (mHeaderViewInfos.size() > 0) {
            boolean result = false;
            if (mAdapter != null && ((HeaderRecyclerViewAdapter)mAdapter).removeHeader(view)) {
                dispatchDataObserverOnChanged();;
                result = true;
            }
            return result;
        }

        return false;
    }

    public void addFooterView(@NonNull View view) {
        final ViewInfo info = ViewInfo.create(view);
        mFooterViewInfos.put(AdapterType.TYPE_FOOTER_BASE - mFooterViewInfos.size(), info);
        wrapAdapter();
    }

    public int getFooterViewsCount() {
        return mFooterViewInfos.size();
    }

    public boolean removeFooterView(@NonNull View view) {
        if (mFooterViewInfos.size() > 0) {
            boolean result = false;
            if (mAdapter != null && ((HeaderRecyclerViewAdapter)mAdapter).removeFooter(view)) {
                dispatchDataObserverOnChanged();;
                result = true;
            }
            return result;
        }

        return false;
    }

    private void wrapAdapter() {
        if (!(mAdapter instanceof HeaderRecyclerViewAdapter)) {
            wrapAdapterInternal(mAdapter, mEmptyViewContainer, mHeaderViewInfos,
                    mFooterViewInfos, mLoadMoreContainer);
            dispatchDataObserverOnChanged();
        }
    }

    private void addLoadMoreListener() {
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                final LayoutManager layoutManager = getLayoutManager();
                if (layoutManager == null) {
                    return;
                }

                int visibleItemCount = layoutManager.getChildCount();
                final View child = getChildAt(getChildCount() - 1);
                int position = getChildLayoutPosition(child);
                if (visibleItemCount > 0 && newState == SCROLL_STATE_IDLE &&
                        position == layoutManager.getItemCount() - 1) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        if (!(loadMoreView instanceof View)) {
            throw new IllegalArgumentException("LoadMoreView should be an android View");
        }

        if (mLoadMoreView != null) {
            mLoadMoreContainer.removeView((View) mLoadMoreView);
        }
        mLoadMoreView = loadMoreView;
        mLoadMoreContainer.addView((View) mLoadMoreView);
    }

    public void setOnLoadMoreListener(@NonNull OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    private class WrapDataObserver extends AdapterDataObserver {
        WrapDataObserver() {
        }

        @Override
        public void onChanged() {
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mAdapter.notifyItemRangeChanged(fromPosition, toPosition);
        }
    }
}
