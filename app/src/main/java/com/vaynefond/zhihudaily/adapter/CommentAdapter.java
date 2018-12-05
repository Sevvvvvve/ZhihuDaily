package com.vaynefond.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vaynefond.zhihudaily.R;
import com.vaynefond.zhihudaily.base.adapter.BaseAdapter;
import com.vaynefond.zhihudaily.base.adapter.BaseViewHolder;
import com.vaynefond.zhihudaily.base.image.ImageLoader;
import com.vaynefond.zhihudaily.data.model.Comment;

import java.util.List;

import butterknife.BindView;

public class CommentAdapter extends BaseAdapter {
    private static final int TYPE_COMMENT = 0;
    private static final int TYPE_SECTION = 1;
    private static final int TYPE_EMPTY = 2;

    private boolean mExpandShortComments;

    private List<Comment> mLongComments;
    private List<Comment> mShortComments;

    private OnExpandListener mOnExpandListener;
    private int mSectionHeight;

    public CommentAdapter(@NonNull Context context) {
        super(context);
    }

    public void bindLongComments(List<Comment> comments) {
        mLongComments = comments;
    }

    public void bindShortComments(List<Comment> comments) {
        mShortComments = comments;
    }

    public void setOnExpandListener(@NonNull OnExpandListener listener) {
        mOnExpandListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSectionHeight = getContext().getResources().getDimensionPixelSize(R.dimen.list_header_height);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(
                    getContext()).inflate(R.layout.comment_empty_layout, parent, false);
            emptyView.getLayoutParams().height = parent.getHeight() - parent.getPaddingTop() -
                    parent.getPaddingBottom() - 2 * mSectionHeight;
            return new EmptyViewHolder(emptyView);
        } else if (viewType == TYPE_SECTION) {
            return new CommentSectionViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.comment_section_item, parent, false));
        } else {
            return new CommentViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.comment_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_SECTION) {
            CommentSectionViewHolder sectionHolder = (CommentSectionViewHolder) holder;
            bindSection(sectionHolder, position);
        } else if (viewType == TYPE_COMMENT) {
            CommentViewHolder commentHolder = (CommentViewHolder) holder;
            final Comment comment = getCommentByPosition(position);
            bindCommentData(commentHolder, comment);
        }
    }

    private void bindSection(CommentSectionViewHolder sectionHolder, int position) {
        if (position == 0) {
            sectionHolder.mHeaderTextView.setText(getContext().getString(
                    R.string.long_comments, String.valueOf(mLongComments == null? 0 : mLongComments.size())));
        } else {
            sectionHolder.mHeaderTextView.setText(getContext().getString(
                    R.string.short_comments, String.valueOf(mShortComments == null? 0 : mShortComments.size())));
            sectionHolder.mHeaderIcon.setVisibility(View.VISIBLE);
            sectionHolder.mHeaderIcon.setRotation(mExpandShortComments ? 180 : 0);
            sectionHolder.itemView.setOnClickListener(v -> {
                mExpandShortComments = !mExpandShortComments;
                notifyDataSetChanged();

                if (mOnExpandListener != null) {
                    mOnExpandListener.onExpand(mExpandShortComments, position);
                }
            });
        }
    }

    private Comment getCommentByPosition(int position) {
        int longSectionIndex = 0;
        int shortSectionIndex;
        if (mLongComments == null || mLongComments.isEmpty()) {
            shortSectionIndex = 2;
        } else {
            shortSectionIndex = 1 + mLongComments.size();
        }

        if (position > longSectionIndex && position < shortSectionIndex) {
            return mLongComments.get(position - 1);
        } else {
            return mShortComments.get(position - shortSectionIndex - 1);
        }
    }

    private void bindCommentData(@NonNull CommentViewHolder holder, @NonNull Comment comment) {
        ImageLoader.loadImage(holder.mAvatar, comment.getAvatar());
        holder.mAuthor.setText(comment.getAuthor());
        holder.mCommentTime.setText(String.valueOf(comment.getTime()));
        holder.mLikeCount.setText(String.valueOf(comment.getLikes()));
        holder.mContent.setText(comment.getContent());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_SECTION;
        }

        if (mLongComments == null || mLongComments.isEmpty()) {
            if (position == 1) {
                return TYPE_EMPTY;
            } else if (position == 2) {
                return TYPE_SECTION;
            } else {
                return TYPE_COMMENT;
            }
        }

        if (position == mLongComments.size() + 1) {
            return TYPE_SECTION;
        }

        return TYPE_COMMENT;
    }

    @Override
    public int getItemCount() {
        int count = 2;

        if (mLongComments == null || mLongComments.isEmpty()) {
            count++;
        } else {
            count += mLongComments.size();
        }

        if (mExpandShortComments) {
            count += mShortComments == null ? 0 : mShortComments.size();
        }

        return count;
    }

    class EmptyViewHolder extends BaseViewHolder {
        @BindView(R.id.comment_empty)
        RelativeLayout mEmptyLayout;

        @BindView(R.id.comment_empty_image)
        ImageView mEmptyImage;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class CommentSectionViewHolder extends BaseViewHolder {
        @BindView(R.id.comment_header_layout)
        ViewGroup mHeaderLayout;

        @BindView(R.id.comment_header_text)
        TextView mHeaderTextView;

        @BindView(R.id.comment_header_icon)
        ImageView mHeaderIcon;

        CommentSectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class CommentViewHolder extends BaseViewHolder {
        @BindView(R.id.comment_item_avatar)
        ImageView mAvatar;

        @BindView(R.id.comment_item_author)
        TextView mAuthor;

        @BindView(R.id.comment_item_like_count)
        TextView mLikeCount;

        @BindView(R.id.comment_item_content)
        TextView mContent;

        @BindView(R.id.comment_item_time)
        TextView mCommentTime;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnExpandListener {
        void onExpand(boolean expanded, int position);
    }
}
