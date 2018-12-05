package com.vaynefond.zhihudaily.base.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.vaynefond.zhihudaily.base.adapter.BaseViewHolder;

class HeaderRecyclerViewAdapter<VH> extends WrapperRecyclerViewAdapter {
    private RecyclerView.Adapter mAdapter;

    private View mEmptyView;
    private SparseArray<ViewInfo> mHeaderViewInfos;
    private SparseArray<ViewInfo> mFooterViewInfos;
    private View mLoadMoreView;

    public HeaderRecyclerViewAdapter(@NonNull RecyclerView.Adapter adapter, @NonNull View emptyView,
             @NonNull SparseArray<ViewInfo> headerViewInfos, @NonNull SparseArray<ViewInfo> footerViewInfos,
             @NonNull View loadMoreView) {
        mAdapter = adapter;
        mEmptyView = emptyView;
        mHeaderViewInfos = headerViewInfos;
        mFooterViewInfos = footerViewInfos;
        mLoadMoreView = loadMoreView;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int viewType = getItemViewType(holder.getLayoutPosition());
        if (isDecorType(viewType)) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        }

        if (mAdapter != null) {
            mAdapter.onViewAttachedToWindow(holder);
        }
    }

    private boolean isDecorType(int type) {
        return isEmtpyView(type) || isHeader(type) ||
                isFooter(type) || isLoadMoreView(type);
    }

    private boolean isEmtpyView(int type) {
        return type == AdapterType.TYPE_EMPTY_VIEW;
    }

    private boolean isHeader(int type) {
        return type >= AdapterType.TYPE_HEADER_BASE &&
                type < AdapterType.TYPE_HEADER_BASE + mHeaderViewInfos.size();
    }

    private boolean isFooter(int type) {
        return type <= AdapterType.TYPE_FOOTER_BASE &&
                type > AdapterType.TYPE_FOOTER_BASE - mFooterViewInfos.size();
    }

    private boolean isLoadMoreView(int type) {
        return type == AdapterType.TYPE_LOAD_MORE;
    }

    public int getHeaderCount() {
        return mHeaderViewInfos.size();
    }

    public int getFooterCount() {
        return mFooterViewInfos.size();
    }

    private View getHeaderView(int type) {
        return mHeaderViewInfos.get(type).view;
    }

    private View getFooterView(int type) {
        return mFooterViewInfos.get(type).view;
    }

    public boolean removeHeader(@NonNull View view) {
        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
            final View header = mHeaderViewInfos.valueAt(i).view;
            if (view == header) {
                mHeaderViewInfos.removeAt(i);
                return true;
            }
        }

        return false;
    }

    public boolean removeFooter(@NonNull View view) {
        for (int i = 0; i < mFooterViewInfos.size(); i++) {
            final View footer = mFooterViewInfos.valueAt(i).view;
            if (view == footer) {
                mFooterViewInfos.removeAt(i);
                return true;
            }
        }

        return false;
    }

    @Override
    RecyclerView.Adapter getWrappedAdapter() {
        return mAdapter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isEmtpyView(viewType)) {
            return BaseViewHolder.create(mEmptyView);
        } else if (isHeader(viewType)) {
            return BaseViewHolder.create(getHeaderView(viewType));
        } else if (isFooter(viewType)) {
            return BaseViewHolder.create(getFooterView(viewType));
        } else if (isLoadMoreView(viewType)) {
            return BaseViewHolder.create(mLoadMoreView);
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int type = getItemViewType(position);
        if (isDecorType(type)) {
            return;
        }

        int headerCount = getHeaderCount();
        final int adjPosition = position - headerCount;
        if (mAdapter != null) {
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int headerCount = getHeaderCount();
        int footerCount = getFooterCount();

        if (mAdapter == null || mAdapter.getItemCount() == 0) {
            if (headerCount == 0 && footerCount == 0) {
                return AdapterType.TYPE_EMPTY_VIEW;
            }
        }

        if (position < headerCount) {
            return AdapterType.TYPE_HEADER_BASE + position;
        }

        final int adjPosition = position - headerCount;
        if (mAdapter != null) {
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }

        if (position == getItemCount() - 1) {
            return AdapterType.TYPE_LOAD_MORE;
        }

        return AdapterType.TYPE_FOOTER_BASE - (getItemCount() - position - 2);
    }

    @Override
    public int getItemCount() {
        int itemCount = getHeaderCount() + getFooterCount() + 1;
        if (mAdapter != null) {
            itemCount += mAdapter.getItemCount();
        }

        if (itemCount == 0) {
            itemCount = 1;
        }

        return itemCount;
    }

    @Override
    public long getItemId(int position) {
        int headerCount = getHeaderCount();
        if (mAdapter != null && position > headerCount) {
            int adjPosition = position - headerCount;
            if (hasStableIds()) {
                adjPosition--;
            }

            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemId(adjPosition);
            }
        }

        return -1;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (mAdapter != null) {
            mAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mAdapter != null) {
            mAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }
}
