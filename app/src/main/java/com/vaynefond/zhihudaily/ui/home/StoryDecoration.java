package com.vaynefond.zhihudaily.ui.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vaynefond.zhihudaily.R;

public class StoryDecoration extends RecyclerView.ItemDecoration {
    private final Context mContext;
    private StoryInfoListener mStoryInfoListener;

    private int mHeaderColor;
    private int mDateColor;

    private Paint mPaint;

    private int mHeaderHeight;
    private int mDateMarginLeft;

    public StoryDecoration(@NonNull Context context, StoryInfoListener listener) {
        mContext = context;
        mStoryInfoListener = listener;
        init();
    }

    private void init() {
        mHeaderHeight = mContext.getResources().getDimensionPixelSize(R.dimen.paper_header_height);
        mDateMarginLeft = mContext.getResources().getDimensionPixelSize(R.dimen.date_margin_left);

        mHeaderColor = mContext.getResources().getColor(R.color.transparent);
        mDateColor = mContext.getResources().getColor(R.color.paper_date_color);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (mStoryInfoListener != null) {
                StoryInfo info = mStoryInfoListener.getStoryInfo(position);
                if (info != null) {
                    int left, top, right, bottom;

                    left = parent.getPaddingLeft();
                    right = parent.getWidth() - parent.getPaddingRight();
                    if (info.isFirstElement()) {
                        top = child.getTop() - mHeaderHeight;
                        bottom = child.getTop();
                        drawDate(c, left, top, right, bottom, info);
                    }
                }
            }
        }
    }

    private void drawDate(Canvas c, int left, int top, int right, int bottom, StoryInfo info) {
        mPaint.setColor(mHeaderColor);
        c.drawRect(left, top, right, bottom, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mDateColor);
        mPaint.setStrokeWidth(10);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_16));
        mPaint.setTypeface(Typeface.create(
                mContext.getString(R.string.date_font_family), Typeface.NORMAL));

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = (top + bottom) / 2 + distance;

        final String date = info.getDate();
        Rect rect = new Rect();
        mPaint.getTextBounds(date, 0, date.length(), rect);
        c.drawText(date, left + mDateMarginLeft + rect.width() / 2, baseline, mPaint);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        if (position == 0) {
            return;
        }

        if (mStoryInfoListener != null) {
            StoryInfo info = mStoryInfoListener.getStoryInfo(position);
            if (info != null) {
                if (info.isFirstElement()) {
                    outRect.top = mHeaderHeight;
                }
            }
        }
    }

    public interface StoryInfoListener {
        StoryInfo getStoryInfo(int position);
    }
}
