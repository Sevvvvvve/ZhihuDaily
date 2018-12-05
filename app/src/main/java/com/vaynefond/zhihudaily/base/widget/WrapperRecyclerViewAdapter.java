package com.vaynefond.zhihudaily.base.widget;

import android.support.v7.widget.RecyclerView;

abstract class WrapperRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    abstract RecyclerView.Adapter<VH> getWrappedAdapter();
}
