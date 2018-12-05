package com.vaynefond.zhihudaily.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = TopStory.Scheme.TABLE_NAME)
public class TopStory {
    /**
     * image : https://pic2.zhimg.com/v2-07911a9a9d4946d10b9811524c535bb5.jpg
     * type : 0
     * id : 9694555
     * ga_prefix : 083013
     * title : 自如这样的长租公寓是怎样抬高房租的？举一个盒饭的例子你就懂了
     */

    static class Scheme {
        static final String TABLE_NAME = "top_story";

        static class Column {
            public static final String ID = "id";

            public static final String TYPE = "type";

            public static final String GA_PREFIX = "ga_prefix";

            public static final String TITLE = "title";

            public static final String IMAGE = "image";
        }
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Column.ID)
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = Scheme.Column.IMAGE)
    @SerializedName("image")
    private String mImage;

    @ColumnInfo(name = Scheme.Column.TYPE)
    @SerializedName("type")
    private int mType;

    @ColumnInfo(name = Scheme.Column.GA_PREFIX)
    @SerializedName("ga_prefix")
    private String mGaPrefix;

    @ColumnInfo(name = Scheme.Column.TITLE)
    @SerializedName("title")
    private String mTitle;

    public static TopStory objectFromData(String str) {
        return new Gson().fromJson(str, TopStory.class);
    }

    public static TopStory objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), TopStory.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
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
}
