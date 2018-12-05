package com.vaynefond.zhihudaily.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = StoryExtra.Scheme.TABLE_NAME)
public class StoryExtra {

    /**
     * long_comments : 5
     * popularity : 1449
     * short_comments : 120
     * comments : 125
     */

    static class Scheme {
        public static final String TABLE_NAME = "story_extra";

        static class Column {
            public static final String ID = "id";

            public static final String LONG_COMMENTS = "long_comments";

            public static final String POPULARITY = "popularity";

            public static final String SHORT_COMMENTS = "short_comments";

            public static final String COMMENTS = "comments";
        }
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Column.ID)
    private int mId;

    @ColumnInfo(name = Scheme.Column.LONG_COMMENTS)
    @SerializedName("long_comments")
    private int mLongComments;

    @ColumnInfo(name = Scheme.Column.POPULARITY)
    @SerializedName("popularity")
    private int mPopularity;

    @ColumnInfo(name = Scheme.Column.SHORT_COMMENTS)
    @SerializedName("short_comments")
    private int mShortComments;

    @ColumnInfo(name = Scheme.Column.COMMENTS)
    @SerializedName("comments")
    private int mComments;

    public static StoryExtra objectFromData(String str) {
        return new Gson().fromJson(str, StoryExtra.class);
    }

    public static StoryExtra objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), StoryExtra.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLongComments() {
        return mLongComments;
    }

    public void setLongComments(int longComments) {
        mLongComments = longComments;
    }

    public int getPopularity() {
        return mPopularity;
    }

    public void setPopularity(int popularity) {
        mPopularity = popularity;
    }

    public int getShortComments() {
        return mShortComments;
    }

    public void setShortComments(int shortComments) {
        mShortComments = shortComments;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int comments) {
        mComments = comments;
    }
}
