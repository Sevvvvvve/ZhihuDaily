package com.vaynefond.zhihudaily.base.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleLoadMoreView extends FrameLayout implements LoadMoreView {
    @BindView(R.id.simple_loading)
    ProgressBar mLoadingView;

    @BindView(R.id.simple_load_error)
    TextView mErrorView;

    @BindView(R.id.simple_load_end)
    TextView mLoadEndView;

    private OnRetryListener mOnRetryListener;
    private Status mStatus;

    enum Status {
        GONE,
        LOADING,
        LOAD_ERROR,
        LOAD_END
    }

    public SimpleLoadMoreView(@NonNull Context context) {
        this(context ,null);
    }

    public SimpleLoadMoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLoadMoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.simple_load_more_view, this, true);
        ButterKnife.bind(this);
        setStatus(Status.GONE);

        mErrorView.setOnClickListener(v -> {
            if (mOnRetryListener != null) {
                mOnRetryListener.onRetry();
            }
        });
    }

    private void setLoading(boolean loading) {
        if (loading) {
            setStatus(Status.LOADING);
        } else {
            setStatus(Status.GONE);
        }
    }

    private void setLoadError(String message) {
        mErrorView.setText(message);
        setStatus(Status.LOAD_ERROR);
    }

    private void setLoadEnd() {
        setStatus(Status.LOAD_END);
    }

    private void setStatus(Status status) {
        mStatus = status;
        update();
    }

    private void update() {
        switch (mStatus) {
            case GONE:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mLoadEndView.setVisibility(GONE);
                break;
            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mLoadEndView.setVisibility(GONE);
                break;
            case LOAD_ERROR:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(VISIBLE);
                mLoadEndView.setVisibility(GONE);
                break;
            case LOAD_END:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mLoadEndView.setVisibility(VISIBLE);
                break;
        }
    }

    @Override
    public void onLoading() {
        setLoading(true);
    }

    @Override
    public void onLoadComplete() {
        setLoading(false);
    }

    @Override
    public void onLoadError(String message) {
        setLoadError(message);
    }

    @Override
    public void onLoadEnd() {
        setLoadEnd();
    }

    public void setOnRetryListener(@NonNull OnRetryListener listener) {
        mOnRetryListener = listener;
    }

    public interface OnRetryListener {
        void onRetry();
    }
}
