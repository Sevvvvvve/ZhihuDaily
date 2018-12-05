package com.vaynefond.zhihudaily.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Entity(tableName = Story.Scheme.TABLE_NAME)
public class Story {
    /**
     * images : ["https://pic4.zhimg.com/v2-f0d9898b37c0d2f55815b3575fb2add3.jpg"]
     * type : 0
     * id : 9694388
     * ga_prefix : 083019
     * title : 用吹风机吹个头发，里面门道还挺多的
     * multipic : true
     */

    static class Scheme {
        static final String TABLE_NAME = "story";

        static class Column {
            static final String ID = "id";

            static final String TYPE = "type";

            static final String GA_PREFIX = "ga_prefix";

            static final String TITLE = "title";

            static final String IMAGES = "images";

            static final String MULTIPIC = "multipic";

            static final String DATE = "date";

            static final String FAVORITE = "favorite";

            static final String FAVORITE_DATE = "favorite_date";
        }
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Column.ID)
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = Scheme.Column.TYPE)
    @SerializedName("type")
    private int mType;

    @ColumnInfo(name = Scheme.Column.GA_PREFIX)
    @SerializedName("ga_prefix")
    private String mGaPrefix;

    @ColumnInfo(name = Scheme.Column.TITLE)
    @SerializedName("title")
    private String mTitle;

    @ColumnInfo(name = Scheme.Column.MULTIPIC)
    @SerializedName("multipic")
    private boolean mMultipic;

    @ColumnInfo(name = Scheme.Column.IMAGES)
    @SerializedName("images")
    private List<String> mImages;

    @ColumnInfo(name = Scheme.Column.DATE)
    private String mDate;

    @ColumnInfo(name = Scheme.Column.FAVORITE)
    private boolean mFavorite;

    @ColumnInfo(name = Scheme.Column.FAVORITE_DATE)
    private long mFavoriteDate;

    public static Story objectFromData(String str) {
        return new Gson().fromJson(str, Story.class);
    }

    public static Story objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getString(str), Story.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        mGaPrefix = gaPrefix;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isMultipic() {
        return mMultipic;
    }

    public void setMultipic(boolean multipic) {
        mMultipic = multipic;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        mFavorite = favorite;
    }

    public long getFavoriteDate() {
        return mFavoriteDate;
    }

    public void setFavoriteDate(long favoriteDate) {
        mFavoriteDate = favoriteDate;
    }
}
