package com.vaynefond.zhihudaily.ui.story;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.image.ImageLoader;
import com.vaynefond.zhihudaily.base.mvp.BaseMvpFragment;
import com.vaynefond.zhihudaily.base.widget.AppToast;
import com.vaynefond.zhihudaily.data.model.StoryContent;
import com.vaynefond.zhihudaily.data.model.StoryExtra;
import com.vaynefond.zhihudaily.ui.comment.CommentActivity;

import javax.inject.Inject;

import butterknife.BindView;

public class StoryFragment extends BaseMvpFragment<StoryPresenter, StoryContract.View> implements StoryContract.View {
    private static final String ARG_STORY_ID = "arg_story_id";
    private static final int INVALID_ID = -1;
    private int mStoryId = INVALID_ID;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.story_photo)
    ImageView mStoryPhoto;

    @BindView(R.id.story_title)
    TextView mStoryTitle;

    @BindView(R.id.story_image_source)
    TextView mStoryImageSource;

    @BindView(R.id.story_scroll_view)
    NestedScrollView mScrollView;

    @BindView(R.id.story_webview)
    WebView mStoryWebView;

    @BindView(R.id.story_loading_bar)
    ProgressBar mLoadingProgress;

    private TextIconActionMenu mCommentMenu;
    private TextIconActionMenu mPopularityMenu;
    private MenuData mMenuData;
    private boolean mIsFavorite;
    private StoryWebViewHelper mStoryWebViewHelper;

    @Inject
    public StoryFragment() {
    }

    public static StoryFragment newInstance(int storyId) {
        StoryFragment fragment = new StoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STORY_ID, storyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStoryId = getArguments().getInt(ARG_STORY_ID, INVALID_ID);
        }

        if (mStoryId == INVALID_ID) {
            requireActivity().onBackPressed();
        }

        initMenuData();
        startLoadFavorites();
    }

    private void startLoadFavorites() {
        mPresenter.loadFavoriteValue(mStoryId);
        mPresenter.loadStoryExtra(mStoryId);
        mPresenter.loadStoryContent(mStoryId);
    }

    private void initMenuData() {
        mMenuData = new MenuData();
    }

    @Override
    protected int fragmentLayoutId() {
        return R.layout.fragment_story;
    }

    @Override
    protected void setupViews(@NonNull View root) {
        setupToolbar();
        setupWebView();
    }

    private void setupToolbar() {
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
        initToolbarBehavior();
        initToolbarMenu();
        updateToolbarMenu();
    }

    private void initToolbarBehavior() {
        ToolbarBehavior behavior = ToolbarBehavior.from(mToolbar);
        behavior.setOnToolbarCallback(isToolbarShown -> performImmersiveUpdate(!isToolbarShown));
    }

    private void performImmersiveUpdate(boolean immersive) {
        if (requireActivity() instanceof OnWindowAttributeCallback) {
            ((OnWindowAttributeCallback) requireActivity()).updateWindowAttribute(immersive);
        }
        setStatusBarColor(immersive ?
                getThemeColor(R.attr.activityBackground) : Color.TRANSPARENT);
    }

    private void initToolbarMenu() {
        mToolbar.inflateMenu(R.menu.story);
        final Menu menu = mToolbar.getMenu();
        mCommentMenu = new TextIconActionMenu(menu.findItem(R.id.action_comment));
        mPopularityMenu = new TextIconActionMenu(menu.findItem(R.id.action_popularity));
        mToolbar.setOnMenuItemClickListener(this::performMenuItemClicked);
    }

    private boolean performMenuItemClicked(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_share:
                handleActionShare();
                return true;
            case R.id.action_favorite:
                handleActionFavorite();
                return true;
            case R.id.action_comment:
                showCommentInterace();
                return true;
            case R.id.action_popularity:
                AppToast.showLongText(R.string.to_do);
                return true;
        }

        return false;
    }

    private void updateToolbarMenu() {
        final Menu menu = mToolbar.getMenu();
        menu.findItem(R.id.action_share).setIcon(mMenuData.mIconShare);
        menu.findItem(R.id.action_favorite).setIcon(mMenuData.mIconFavorite);
        mCommentMenu.setText(String.valueOf(mMenuData.mCommentCount));
        mCommentMenu.setIcon(R.drawable.comment);
        mPopularityMenu.setText(String.valueOf(mMenuData.mPopularityCount));
        mPopularityMenu.setIcon(R.drawable.praise);
    }

    private void handleActionShare() {
        mPresenter.loadShareLink(mStoryId);
    }

    private void handleActionFavorite() {
        mPresenter.favoriteStory(mStoryId, !mIsFavorite);
    }

    private void showCommentInterace() {
        CommentActivity.start(requireContext(), mStoryId);
    }

    private void setupWebView() {
        mStoryWebViewHelper = new StoryWebViewHelper(mStoryWebView);
        mStoryWebViewHelper.initWebView(mPresenter.isNoImageMode());
    }

    @Override
    public void showStoryContent(@NonNull StoryContent content) {
        ImageLoader.loadImage(mStoryPhoto, content.getImage());
        mStoryTitle.setText(content.getTitle());
        mStoryImageSource.setText(content.getImageSource());
        mStoryWebViewHelper.loadPage(content.getBody(), content.getShareUrl(), mPresenter.isNightMode());
    }

    @Override
    public void showStoryExtra(@NonNull StoryExtra storyExtra) {
        mMenuData.setFavorite(mIsFavorite);
        mMenuData.setCommentCount(storyExtra.getComments());
        updateToolbarMenu();
    }

    @Override
    public void updateFavoriteView(boolean isFavorite) {
        mIsFavorite = isFavorite;
        mMenuData.setFavorite(mIsFavorite);
        updateToolbarMenu();
    }

    @Override
    public void showShareView(String title, String url) {
        if (url == null) {
            AppToast.showLongText(R.string.share_url_not_found);
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url);
        shareIntent = Intent.createChooser(shareIntent, getString(R.string.share));
        startActivity(shareIntent);
    }

    @Override
    public void onDestroyView() {
        if (mStoryWebViewHelper != null) {
            mStoryWebViewHelper.destroyWebView();
        }
        super.onDestroyView();
    }

    class TextIconActionMenu {
        private TextView mTextView;

        TextIconActionMenu(@NonNull MenuItem menuItem) {
            mTextView = menuItem.getActionView().findViewById(R.id.action_view_text_icon);
            mTextView.setOnClickListener(v -> performMenuItemClicked(menuItem));
        }

        public CharSequence getText() {
            return mTextView.getText();
        }

        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        public void setIcon(@DrawableRes int icon) {
            Drawable drawableLeft = requireContext().getDrawable(icon);
            mTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    drawableLeft, null, null, null);
        }
    }

    public interface OnWindowAttributeCallback {
        void updateWindowAttribute(boolean immersive);
    }

    class MenuData {
        private static final int ICON_SHARE = R.drawable.share;
        private static final int ICON_COLLECTED = R.drawable.collected;
        private static final int ICON_COLLECT = R.drawable.collect;

        int mIconShare;
        int mIconFavorite;
        int mCommentCount;
        int mPopularityCount;

        MenuData() {
            this(false);
        }

        MenuData(boolean isFavorite) {
            this(isFavorite, 0);
        }

        MenuData(boolean isFavorite, int commentCount) {
            this(isFavorite, commentCount, 0);
        }

        MenuData(boolean isFavorite, int commentCount, int popularityCount) {
            mIconShare = ICON_SHARE;
            mIconFavorite = isFavorite ? ICON_COLLECTED : ICON_COLLECT;
            mCommentCount = commentCount;
            mPopularityCount = popularityCount;
        }

        void setFavorite(boolean favorite) {
            mIconFavorite = favorite ? ICON_COLLECTED : ICON_COLLECT;
        }

        void setCommentCount(int commentCount) {
            mCommentCount = commentCount;
        }
    }
}
