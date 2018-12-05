package com.vaynefond.zhihudaily.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class ViewUtils {
    public static int findFirstVisibleItemPosition(@NonNull RecyclerView recyclerView) {
        int position = -1;
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager){
            final int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            int[] into = new int[spanCount];
            into = ((StaggeredGridLayoutManager)layoutManager).findFirstCompletelyVisibleItemPositions(into);
            position = findMinInto(into);
        } else if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
        }

        return position;
    }

    public static int findFirstCompletelyVisibleItem(@NonNull RecyclerView recyclerView) {
        int position = -1;
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager){
            final int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            int[] into = new int[spanCount];
            into = ((StaggeredGridLayoutManager)layoutManager).findFirstCompletelyVisibleItemPositions(into);
            position = findMinInto(into);
        } else if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager)layoutManager).findFirstCompletelyVisibleItemPosition();
        }

        return position;
    }

    public static int findLastVisibleItemPosition(@NonNull RecyclerView recyclerView) {
        int position = -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager)layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            final int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            int[] into = new int[spanCount];
            into = ((StaggeredGridLayoutManager)layoutManager).findLastVisibleItemPositions(into);
            position = findMaxInto(into);
        } else if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
        }
        return position;
    }

    private static int findMaxInto(int[] into) {
        int max = into[0];
        for (int pos : into) {
            if (into[pos] > max) {
                max = into[pos];
            }
        }
        return max;
    }

    private static int findMinInto(int[] into) {
        int min = into[0];
        for (int pos : into) {
            if (into[pos] < min) {
                min = into[pos];
            }
        }
        return min;
    }
}
