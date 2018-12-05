package com.vaynefond.zhihudaily.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.vaynefond.zhihudaily.R;

import org.json.JSONException;
import org.json.JSONObject;

import dagger.Provides;

@Entity(tableName = Theme.Scheme.TABLE_NAME)
public class Theme implements Parcelable {
    /**
     * color : 15007
     * thumbnail : http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg
     * description : 了解自己和别人，了解彼此的欲望和局限。
     * id : 13
     * name : 日常心理学
     */

    static class Scheme {
        static final String TABLE_NAME = "theme";

        static class Column {
            static final String ID = "id";

            static final String COLOR = "color";

            static final String THUMBNAIL = "thumbnail";

            static final String DESCRIPTION = "description";

            static final String NAME = "name";
        }
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Column.ID)
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = Scheme.Column.COLOR)
    @SerializedName("color")
    private int mColor;

    @ColumnInfo(name = Scheme.Column.THUMBNAIL)
    @SerializedName("thumbnail")
    private String mThumbnail;

    @ColumnInfo(name = Scheme.Column.DESCRIPTION)
    @SerializedName("description")
    private String mDescription;

    @ColumnInfo(name = Scheme.Column.NAME)
    @SerializedName("name")
    private String mName;

    @Ignore
    private int mLogo;

    @Ignore
    private boolean mIsSubscribed;

    private static final int HOME_THEME_ID = 0;
    private static final Theme HOME_THEME = new Theme.Builder()
            .id(HOME_THEME_ID)
            .name("首页")
            .logo(R.drawable.home)
            .isSubscribed(true)
            .build();

    public Theme() {
    }

    public Theme(Parcel parcel) {
        mColor = parcel.readInt();
        mThumbnail = parcel.readString();
        mDescription = parcel.readString();
        mId = parcel.readInt();
        mName = parcel.readString();
        mLogo = parcel.readInt();
        mIsSubscribed = parcel.readInt() == 1 ? true : false;
    }

    public static Theme objectFromData(String str) {
        return new Gson().fromJson(str, Theme.class);
    }

    public static Theme objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Theme.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getLogo() {
        return mLogo;
    }

    public void setLogo(int logo) {
        mLogo = logo;
    }

    public boolean isSubscribed() {
        return mIsSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        mIsSubscribed = subscribed;
    }

    public static Theme getHomeTheme() {
        return HOME_THEME;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mColor);
        dest.writeString(mThumbnail);
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mLogo);
        if (mIsSubscribed) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
    }

    public static final Creator<Theme> CREATOR =
            new Creator<Theme>() {
                public Theme createFromParcel(Parcel source) {
                    return new Theme(source);
                }

                public Theme[] newArray(int size) {
                    return new Theme[size];
                }
            };

    public static class Builder {
        private int mId;

        private int mColor;

        private String mThumbnail;

        private String mDescription;

        private String mName;

        private int mLogo;

        private boolean mIsSubscribed;

        public Builder() {

        }

        public Builder id(int id) {
            this.mId = id;
            return this;
        }

        public Builder color(int color) {
            this.mColor = color;
            return this;
        }

        public Builder thumbnail(String thumbnail) {
            this.mThumbnail = thumbnail;
            return this;
        }

        public Builder description(String description) {
            this.mDescription = description;
            return this;
        }

        public Builder name(String name) {
            this.mName = name;
            return this;
        }

        public Builder logo(int logo) {
            this.mLogo = logo;
            return this;
        }

        public Builder isSubscribed(boolean isSubscribed) {
            this.mIsSubscribed = isSubscribed;
            return this;
        }

        public Theme build() {
            Theme theme = new Theme();
            theme.mId = mId;
            theme.mColor = mColor;
            theme.mDescription = mDescription;
            theme.mThumbnail = mThumbnail;
            theme.mName = mName;
            theme.mLogo = mLogo;
            theme.mIsSubscribed = mIsSubscribed;

            return theme;
        }
    }
}
