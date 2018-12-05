package com.vaynefond.zhihudaily.ui.splash;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpActivity;
import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.service.CacheService;
import com.vaynefond.zhihudaily.ui.home.DailyActivity;
import com.vaynefond.zhihudaily.utils.AnimationAdapter;
import com.vaynefond.zhihudaily.utils.NetUtils;
import com.vaynefond.zhihudaily.utils.VersionUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseMvpActivity<SplashPresenter, SplashContract.View> implements SplashContract.View {
    private static final int OFFLINE_DOWNLOAD_DELAY_MILLISECONDS = 500;
    private static final int SPLASH_DELAY_MILLISECONDS = 1500;

    @BindView(R.id.splash_image)
    ImageView mSplashView;

    @BindView(R.id.splash_logo)
    ImageView mSplashLogo;

    @BindView(R.id.splash_logo_container)
    View mSplashLogoContainer;

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean shouldHideNavigationBar() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startLogoTranslateAnimation();
        CacheService.start(this);
        checkOfflineDownload();
    }

    private void checkOfflineDownload() {
        if (NetUtils.isWifiConnected(this) && mPresenter.isAutoOffline()) {
            addDisposable(Observable.timer(OFFLINE_DOWNLOAD_DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(aLong -> startOfflineDownload()));
        }
    }

    private void startOfflineDownload() {
        LocalBroadcastManager.getInstance(SplashActivity.this)
                .sendBroadcast(new Intent(CacheService.ACTION_OFFLINE_DOWNLOAD));
    }

    @Override
    public void openDailyPaper() {
        DailyActivity.start(SplashActivity.this);
        finish();
    }

    private void startLogoTranslateAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_logo_container);
        animation.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimatedLogo();
            }
        });
        mSplashLogoContainer.startAnimation(animation);
    }

    private void startAnimatedLogo() {
        AnimatedVectorDrawable animatedVectorDrawable =
                (AnimatedVectorDrawable) getDrawable(R.drawable.splash_logo_circle);
        if (animatedVectorDrawable == null) {
            mSplashLogo.setImageResource(R.drawable.splash_logo_circle_vector);
            startSplashAnimation();
            return;
        }

        mSplashLogo.setImageDrawable(animatedVectorDrawable);
        if (VersionUtils.isAndroidMOrHigher()) {
            animatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback() {
                @Override
                public void onAnimationStart(Drawable drawable) {
                }

                @Override
                public void onAnimationEnd(Drawable drawable) {
                    startSplashAnimation();
                }
            });
        } else {
            addDisposable(Observable.timer(SPLASH_DELAY_MILLISECONDS, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(aLong -> startSplashAnimation()));
        }
        animatedVectorDrawable.start();
    }

    private void startSplashAnimation() {
        mSplashView.setImageResource(R.drawable.splash);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_fade_in_out);
        animation.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                openDailyPaper();
            }
        });
        mSplashView.startAnimation(animation);
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        AppToast.showLongText(errorMsg);
    }
}
