package com.vaynefond.zhihudaily.ui.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.app.BaseActivity;
import com.vaynefond.zhihudaily.base.widget.AppToast;

import butterknife.BindView;

public class CommentActivity extends BaseActivity implements CommentFragment.OnToolbarListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private static final String EXTRA_STORY_ID = "extra_story_id";
    private int mStoryId;

    public static void start(@NonNull Context context, int storyId) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_STORY_ID, storyId);
        context.startActivity(intent);
    }

    @Override
    protected int activityLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
        setupViews();
        showCommentFragment();
    }

    private void initVars() {
        if (getIntent() != null) {
            mStoryId = getIntent().getIntExtra(EXTRA_STORY_ID, -1);
        }
    }

    private void setupViews() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showCommentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        CommentFragment commentFragment = (CommentFragment) fragmentManager
                .findFragmentById(R.id.content_frame);

        if (commentFragment == null) {
            commentFragment = CommentFragment.newInstance(mStoryId);
            fragmentManager.beginTransaction()
                    .add(R.id.content_frame, commentFragment)
                    .show(commentFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_comment_compose) {
            AppToast.showLongText(R.string.to_do);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateCommentTitle(int totalComments) {
        mToolbar.setTitle(getString(R.string.comment, String.valueOf(totalComments)));
    }
}
