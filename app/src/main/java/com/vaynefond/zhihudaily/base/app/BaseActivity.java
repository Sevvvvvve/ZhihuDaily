package com.vaynefond.zhihudaily.base.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.app.ZhihuApp;
import com.vaynefond.zhihudaily.utils.ResourceUtils;
import com.vaynefond.zhihudaily.utils.VersionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final int TRANSITION_ANIM_DURATION = 500;

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Unbinder mUnbinder;
    private List<OnThemeChangedListener> mOnThemeChangedListeners;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);
        setContentView(activityLayoutId());
        setWindowAttributes();
        setUnbinder(ButterKnife.bind(this));
    }

    private void initTheme() {
        if (isNightMode()) {
            setTheme(R.style.DailyThemeDark);
        } else {
            setTheme(R.style.DailyThemeLight);
        }
    }

    protected void setWindowAttributes() {
        Window window = getWindow();

        if (VersionUtils.isAndroidPOrHigher()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (shouldHideNavigationBar()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        if (shouldHideNavigationBar()) {
            uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        }
        decorView.setSystemUiVisibility(uiOptions);
        setStatusBarColor(Color.TRANSPARENT);
    }

    protected boolean shouldHideNavigationBar() {
        return false;
    }

    public void setStatusBarColor(@ColorInt int color) {
        if (VersionUtils.isAndroidPOrHigher()) {
            getWindow().setStatusBarColor(color);
        } else if (VersionUtils.isAndroidNOrHigher()) {
            try {
                @SuppressLint("PrivateApi")
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), color);
            } catch (IllegalAccessException | NoSuchFieldException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (VersionUtils.isAndroidLOrHigher()) {
            getWindow().setStatusBarColor(color);
        }
    }

    protected void setUnbinder(@NonNull Unbinder unbinder) {
        mUnbinder = unbinder;
    }

    protected void addDisposable(@NonNull Disposable disposable){
        mCompositeDisposable.add(disposable);
    }

    protected  <T extends Fragment> void initFragment(@NonNull T injectedFragment,
                                                      @IdRes int containerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        T fragment = (T) fragmentManager.findFragmentById(containerId);
        if (fragment == null) {
            fragment = injectedFragment;
            fragmentManager.beginTransaction()
                    .add(containerId, fragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mCompositeDisposable.dispose();
    }

    protected boolean isNightMode() {
        return ((ZhihuApp)getApplication()).isNightMode();
    }

    public void registerThemeChangedListener(@NonNull OnThemeChangedListener listener) {
        if (mOnThemeChangedListeners == null) {
            mOnThemeChangedListeners = new ArrayList<>();
        }
        mOnThemeChangedListeners.add(listener);
    }

    protected void dispatchThemeChanged() {
        for (OnThemeChangedListener listener : mOnThemeChangedListeners) {
            listener.onThemeChanged();
        }
    }

    protected int getThemeResourceId(int resId) {
        return ResourceUtils.getThemeResourceId(this, resId);
    }

    protected int getThemeColor(int resId) {
        return ResourceUtils.getColor(this, resId);
    }

    protected void startThemeTransitionAnimation() {
        final ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        Bitmap background = getBitmapCacheFromView(decorView);
        if (background != null) {
            View transitionView = new View(this);
            transitionView.setBackground(new BitmapDrawable(getResources(), background));
            ViewGroup.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            decorView.addView(transitionView, params);

            Animator animator = ObjectAnimator.ofFloat(transitionView, "alpha", 1f, 0f);
            animator.setDuration(TRANSITION_ANIM_DURATION);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    decorView.removeView(transitionView);
                }
            });
            animator.start();
        }
    }

    private Bitmap getBitmapCacheFromView(@NonNull View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);

        Bitmap bitmap = null;
        final Bitmap bitmapCache = view.getDrawingCache();
        if (bitmapCache != null) {
            bitmap = Bitmap.createBitmap(bitmapCache);
        }
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    @LayoutRes
    protected abstract int activityLayoutId();

    public interface OnThemeChangedListener {
        void onThemeChanged();
    }
}
