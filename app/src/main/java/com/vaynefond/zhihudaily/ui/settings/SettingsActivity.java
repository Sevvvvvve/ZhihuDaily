package com.vaynefond.zhihudaily.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.app.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class SettingsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    SettingsFragment mSettingsFragment;

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setupSettingsFragment();
    }

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_settings;
    }

    private void initViews() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.settings);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupSettingsFragment() {
        initFragment(mSettingsFragment, R.id.settings_container);
        showSettingsFragment();
    }

    private void showSettingsFragment() {
        SettingsFragment fragment = (SettingsFragment) getSupportFragmentManager().
                findFragmentById(R.id.settings_container);
        if (fragment == null) {
            fragment = mSettingsFragment;
            getSupportFragmentManager().beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
