package com.vaynefond.zhihudaily.ui.story;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.animation.AnimationUtils;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.utils.ColorUtils;
import com.vaynefond.zhihudaily.utils.ResourceUtils;

public class ToolbarBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private static final int TRANSLATE_ANIM_DURATION = 200;
    private int mPrimaryColor;
    private int mStatusBarHeight;
    private int mTopImageHeight;

    private boolean mIsToolbarShown;
    private OnToolbarCallback mOnToolbarCallback;

    public ToolbarBehavior() {
    }

    public ToolbarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mPrimaryColor = ResourceUtils.getColor(context, R.attr.colorPrimary);
        mStatusBarHeight = context.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        mTopImageHeight = context.getResources().getDimensionPixelOffset(R.dimen.top_image_height);
        mIsToolbarShown = true;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull Toolbar child, int layoutDirection) {
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child,
                                       @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes, int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child,
                               @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed, int type) {
        if (target instanceof NestedScrollView) {
            final NestedScrollView scrollView = (NestedScrollView) target;
            if (scrollView.getScrollY() < mTopImageHeight - mStatusBarHeight) {
                float offsetFactor = Math.abs(scrollView.getScrollY()) * 1.0f / (mTopImageHeight - mStatusBarHeight);
                int appBarColor = ColorUtils.alpha(mPrimaryColor, offsetFactor);
                child.setBackgroundColor(appBarColor);
            } else {
                child.setBackgroundColor(mPrimaryColor);
                if (dyConsumed > 0 && mIsToolbarShown) {
                    hideToolbar(child);
                } else if (dyConsumed < 0 && !mIsToolbarShown) {
                    showToolbar(child);
                }
            }
        }
    }

    private void showToolbar(Toolbar child) {
        child.animate().translationY(0)
                .alpha(1f)
                .setDuration(TRANSLATE_ANIM_DURATION)
                .setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIsToolbarShown = true;
                        postOnToolbarUpdate(mIsToolbarShown);
                    }
                }).start();
    }

    private void hideToolbar(Toolbar child) {
        child.animate().translationY(-child.getBottom())
                .alpha(0f)
                .setDuration(TRANSLATE_ANIM_DURATION)
                .setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIsToolbarShown = false;
                        postOnToolbarUpdate(mIsToolbarShown);
                    }
                }).start();
    }

    private void postOnToolbarUpdate(boolean isToolbarShown) {
        if (mOnToolbarCallback != null) {
            mOnToolbarCallback.updateStatusbar(isToolbarShown);
        }
    }

    public void setOnToolbarCallback(@NonNull OnToolbarCallback onToolbarCallback) {
        mOnToolbarCallback = onToolbarCallback;
    }

    public interface OnToolbarCallback {
        void updateStatusbar(boolean isToolbarShown);
    }

    public static ToolbarBehavior from(@NonNull Toolbar toolbar) {
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("Toolbar should be a child of CoordinatorLayout");
        }

        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
        if (!(behavior instanceof ToolbarBehavior)) {
            throw new IllegalArgumentException("Toolbar should be associated with ToolbarBehavior");
        }

        return (ToolbarBehavior) behavior;
    }
}
