package com.vaynefond.zhihudaily.ui.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.adapter.ThemeAdapter;
import com.vaynefond.zhihudaily.base.app.BaseActivity;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpFragment;
import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.base.widget.BaseRecyclerView;
import com.vaynefond.zhihudaily.data.model.Theme;
import com.vaynefond.zhihudaily.service.CacheService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NavigationFragment extends BaseMvpFragment<NavigationPresenter, NavigationContract.View>
        implements NavigationContract.View, BaseActivity.OnThemeChangedListener {
    @BindView(R.id.drawer_nav_recyclerview)
    BaseRecyclerView mThemeRecyclerView;

    private View mDrawerHeader;
    private OnNavigationListener mOnNavigationListener;
    private ThemeAdapter mThemeAdapter;

    @Inject
    public NavigationFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity) {
            ((BaseActivity) context).registerThemeChangedListener(this);
        }

        if (context instanceof OnNavigationListener) {
            mOnNavigationListener = (OnNavigationListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadThemes();
    }

    @Override
    protected int fragmentLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void setupViews(@NonNull View root) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false);
        mThemeRecyclerView.setLayoutManager(layoutManager);
        mThemeAdapter = new ThemeAdapter(requireContext());
        mThemeAdapter.bindData(initThemeData(Collections.emptyList()));
        mThemeAdapter.setOnItemClickListener((view, position) -> {
            if (position == 0) {
                if (mOnNavigationListener != null) {
                    mOnNavigationListener.openHomeInterface();
                }
            }
        });
        mThemeRecyclerView.setAdapter(mThemeAdapter);
        addDrawerHeader();
    }

    private void addDrawerHeader() {
        mDrawerHeader = LayoutInflater.from(getContext())
                .inflate(R.layout.navigation_drawer_header, mThemeRecyclerView, false);
        mDrawerHeader.findViewById(R.id.drawer_nav_user).setOnClickListener(v -> {
            if (mOnNavigationListener != null) {
                mOnNavigationListener.openLoginInterface();
            }
        });
        mDrawerHeader.findViewById(R.id.drawer_nav_favorite).setOnClickListener(v -> {
            if (mOnNavigationListener != null) {
                mOnNavigationListener.openFavoriteInterface();
            }
        });
        mDrawerHeader.findViewById(R.id.drawer_nav_offline).setOnClickListener(v -> {
            AppToast.showLongText(R.string.offline_download_prompt);
            LocalBroadcastManager.getInstance(requireContext()).
                    sendBroadcast(new Intent(CacheService.ACTION_OFFLINE_DOWNLOAD));
        });
        mThemeRecyclerView.addHeaderView(mDrawerHeader);
    }

    @Override
    public void showThemes(@NonNull List<Theme> themes) {
        mThemeAdapter.bindData(initThemeData(themes));
        if (mThemeRecyclerView.getAdapter() != null) {
            mThemeRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @NonNull
    private List<Theme> initThemeData(@NonNull List<Theme> themes) {
        List<Theme> adapterThemes = new ArrayList<>();
        adapterThemes.add(Theme.getHomeTheme());
        adapterThemes.addAll(themes);
        return adapterThemes;
    }

    @Override
    public void onThemeChanged() {
        updateDrawerHeader();
        updateNavigationDrawer();
    }

    private void updateDrawerHeader() {
        int drawerBackgroundPrimary = getThemeColor(R.attr.drawerBackgroundPrimary);
        mDrawerHeader.setBackgroundColor(drawerBackgroundPrimary);
    }

    private void updateNavigationDrawer() {
        int drawerItemBackground = getThemeColor(R.attr.drawerBackgroundItemPrimary);
        mThemeRecyclerView.setBackgroundColor(drawerItemBackground);

        for (int i = 1; i < mThemeRecyclerView.getChildCount(); i++) {
            final View child = mThemeRecyclerView.getChildAt(i);

            int navBackgroundNormal = getThemeColor(R.attr.navBackgroundNormal);
            child.setBackgroundColor(navBackgroundNormal);

            TextView title = child.findViewById(R.id.nav_item_title);
            if (title != null) {
                title.setTextColor(getThemeColor(R.attr.itemTitleUnreadTextColor));
            }
        }
        invalidateStoryRecyclerView(mThemeRecyclerView);
    }
}
