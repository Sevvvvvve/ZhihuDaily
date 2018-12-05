package com.vaynefond.zhihudaily.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@Entity(tableName = StoryContent.Scheme.TABLE_NAME)
public class StoryContent {

    /**
     * body : 这个团 30 人，都是七十几岁的老年，资料上只知道是某个一线城市一个什么企业给退休职工报的团。第二天我就觉得这个团很不对劲，很安静，也不爱拍照，就安安静静地买，安安静静地看，安安静静地买（购买力惊人），不管老头老太都酒量惊人。我本身比较内向也不是导游专业，不知道怎么互动和调动气氛，话也很少，于是在喧闹的景区只有我们三十一个安安静静的人，度过了安安静静地六天。
     * image_source : 《荆轲刺秦王》
     * title : 瞎扯 · 如何正确地吐槽
     * image : https://pic1.zhimg.com/v2-73c02c8640ffb3dffaebb148f7b34080.jpg
     * share_url : http://daily.zhihu.com/story/9694412
     * js : []
     * id : 9694412
     * ga_prefix : 083006
     * images : ["https://pic4.zhimg.com/v2-ea829a9c9e445f4cade3bd52b475a103.jpg"]
     * type : 0
     * section : {"thumbnail":"https://pic4.zhimg.com/v2-ea829a9c9e445f4cade3bd52b475a103.jpg","name":"瞎扯","id":2}
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    static class Scheme {
        static final String TABLE_NAME = "story_content";

        static class Column {
            public static final String ID = "id";

            public static final String TYPE = "type";

            public static final String GA_PREFIX = "ga_prefix";

            public static final String TITLE = "title";

            public static final String BODY = "body";

            public static final String IMAGES = "images";

            public static final String IMAGE = "image";

            public static final String IMAGE_SOURCE = "image_source";

            public static final String SHARE_URL = "share_url";

            public static final String JS = "js";

            public static final String CSS = "css";

            public static final String THUMBNAIL = "thumbnail";

            public static final String NAME = "name";
        }
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Column.ID)
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = Scheme.Column.BODY)
    @SerializedName("body")
    private String mBody;

    @ColumnInfo(name = Scheme.Column.IMAGE_SOURCE)
    @SerializedName("image_source")
    private String mImageSource;

    @ColumnInfo(name = Scheme.Column.TITLE)
    @SerializedName("title")
    private String mTitle;

    @ColumnInfo(name = Scheme.Column.IMAGE)
    @SerializedName("image")
    private String mImage;

    @ColumnInfo(name = Scheme.Column.SHARE_URL)
    @SerializedName("share_url")
    private String mShareUrl;

    @ColumnInfo(name = Scheme.Column.GA_PREFIX)
    @SerializedName("ga_prefix")
    private String mGaPrefix;

    @ColumnInfo(name = Scheme.Column.TYPE)
    @SerializedName("type")
    private int mType;

    @Ignore
    @SerializedName("section")
    private Section mSection;

    @Ignore
    @SerializedName("js")
    private List<?> mJs;

    @ColumnInfo(name = Scheme.Column.IMAGES)
    @SerializedName("images")
    private List<String> mImages;

    @ColumnInfo(name = Scheme.Column.CSS)
    @SerializedName("css")
    private List<String> mCss;

    public static StoryContent objectFromData(String str) {
        return new Gson().fromJson(str, StoryContent.class);
    }

    public static StoryContent objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), StoryContent.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getImageSource() {
        return mImageSource;
    }

    public void setImageSource(String imageSource) {
        mImageSource = imageSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
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

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public Section getSection() {
        return mSection;
    }

    public void setSection(Section section) {
        mSection = section;
    }

    public List<?> getJs() {
        return mJs;
    }

    public void setJs(List<?> js) {
        mJs = js;
    }

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }

    public List<String> getCss() {
        return mCss;
    }

    public void setCss(List<String> css) {
        mCss = css;
    }

    public static class Section {
        /**
         * thumbnail : https://pic4.zhimg.com/v2-ea829a9c9e445f4cade3bd52b475a103.jpg
         * name : 瞎扯
         * id : 2
         */

        @SerializedName("id")
        private int mId;

        @ColumnInfo(name = Scheme.Column.THUMBNAIL)
        @SerializedName("thumbnail")
        private String mThumbnail;

        @ColumnInfo(name = Scheme.Column.NAME)
        @SerializedName("name")
        private String mName;

        public static Section objectFromData(String str) {
            return new Gson().fromJson(str, Section.class);
        }

        public static Section objectFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), Section.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getThumbnail() {
            return mThumbnail;
        }

        public void setThumbnail(String thumbnail) {
            mThumbnail = thumbnail;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }
    }

}
