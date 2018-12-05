package com.vaynefond.zhihudaily.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.app.ZhihuApp;
import com.vaynefond.zhihudaily.base.app.BaseActivity;
import com.vaynefond.zhihudaily.base.app.BaseFragment;
import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.ui.favorite.FavoriteFragment;
import com.vaynefond.zhihudaily.ui.navigation.OnNavigationListener;
import com.vaynefond.zhihudaily.ui.settings.SettingsActivity;
import com.vaynefond.zhihudaily.utils.ComponentUtils;

import javax.inject.Inject;

import butterknife.BindView;

public class DailyActivity extends BaseActivity implements OnNavigationListener,
        BaseFragment.OnFragmentInteractionListener {
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    HomeFragment mHomeFragment;

    @Inject
    FavoriteFragment mFavoriteFragment;

    public static void start(@NonNull Context context) {
        ComponentUtils.startActivity(context, DailyActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();
        initFragments();
        showHomeFragment();
    }

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_daily;
    }

    private void setupViews() {
        setStatusBarColor(Color.TRANSPARENT);
        setupToolbar();
        setupDrawerLayout();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerLayout() {
        mDrawerLayout.setStatusBarBackgroundColor(getThemeColor(R.attr.colorPrimary));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initFragments() {
        initFragment(mHomeFragment, R.id.daily_content_frame);
        initFragment(mFavoriteFragment, R.id.daily_content_frame);
    }

    private void showHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mHomeFragment)
                .hide(mFavoriteFragment)
                .commit();
    }

    private void showFavoriteFragment() {
        getSupportFragmentManager().beginTransaction()
                .show(mFavoriteFragment)
                .hide(mHomeFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int lightDarkModeIcon = getThemeResourceId(R.attr.lightDarkModeIcon);
        menu.findItem(R.id.action_light_dark_mode).setIcon(lightDarkModeIcon);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_light_dark_mode:
                handleNightModeChanged();
                return true;
            case R.id.action_settings:
                handleActionSettings();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void handleActionSettings() {
        SettingsActivity.start(DailyActivity.this);
    }

    @Override
    public void openHomeInterface() {
        showHomeFragment();
        closeDrawer();
    }

    @Override
    public void openThemeInterface() {
        AppToast.showLongText(R.string.theme_api_not_supported);
        closeDrawer();
    }

    @Override
    public void openLoginInterface() {
        AppToast.showLongText(R.string.login_not_implement);
        closeDrawer();
    }

    @Override
    public void openFavoriteInterface() {
        showFavoriteFragment();
        closeDrawer();
    }

    @Override
    public void updateToolBarColor(int color) {
        mToolbar.setBackgroundColor(color);
    }

    @Override
    public void updateToolBarTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void handleNightModeChanged() {
        ZhihuApp app = (ZhihuApp) getApplication();
        boolean isNightMode = app.isNightMode();
        if (isNightMode) {
            setTheme(R.style.DailyThemeLight);
        } else {
            setTheme(R.style.DailyThemeDark);
        }

        app.setNightMode(!isNightMode);
        updateViews();
        dispatchThemeChanged();
    }

    private void updateViews() {
        startThemeTransitionAnimation();
        invalidateOptionsMenu();
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            closeDrawer();
            return;
        }
        super.onBackPressed();
    }
}
