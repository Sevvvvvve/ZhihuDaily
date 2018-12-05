package com.vaynefond.zhihudaily.base.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public static BaseViewHolder create(@NonNull View itemView) {
        return new BaseViewHolder(itemView);
    }

    public static BaseViewHolder create(@NonNull LayoutInflater layoutInflater,
                @LayoutRes int layoutId, @NonNull ViewGroup parent) {
        return create(layoutInflater.inflate(layoutId, parent, false));
    }
}
