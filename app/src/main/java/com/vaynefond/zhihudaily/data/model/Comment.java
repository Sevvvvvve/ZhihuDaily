package com.vaynefond.zhihudaily.data.model;

import android.arch.persistence.room.Ignore;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
    /**
     * author : andy小陆
     * content : 《小仙有毒》里，温家的入室弟子考题有一年是“硕鼠”。温家大伯温吞海当年应试的毒方是采尽天下至甜至香之物，密炼熬成一碗甜羹，无毒且馥郁甘甜。但人一旦饮下此羹，尝到了那绝世甜香，之后哪怕是喝蜂蜜也会觉得腥臭苦涩无比，止不住的呕吐，从此世上可食之物就只剩一个味道：苦，最终竟把人活活饿死！正应了考题“硕鼠”。。
     * avatar : http://pic3.zhimg.com/4953f864a_im.jpg
     * time : 1479737963
     * id : 27279755
     * likes : 9
     * reply_to : {"content":"第二个机灵抖的还是有逻辑问题，不该说忘了，应该说没喝过啊我也不知道","status":0,"id":27275308,"author":"2233155495"}
     */

    @SerializedName("id")
    private int mId;

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("content")
    private String mContent;

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("time")
    private int mTime;

    @SerializedName("likes")
    private int mLikes;

    @Ignore
    @SerializedName("reply_to")
    private ReplyTo mReplyTo;

    public static Comment objectFromData(String str) {
        return new Gson().fromJson(str, Comment.class);
    }

    public static Comment objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getString(str), Comment.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        mLikes = likes;
    }

    public ReplyTo getReplyTo() {
        return mReplyTo;
    }

    public void setReplyTo(ReplyTo replyTo) {
        mReplyTo = replyTo;
    }

    public static class ReplyTo {
        /**
         * content : 第二个机灵抖的还是有逻辑问题，不该说忘了，应该说没喝过啊我也不知道
         * status : 0
         * id : 27275308
         * author : 2233155495
         */

        @SerializedName("content")
        private String mContent;

        @SerializedName("status")
        private int mStatus;

        @SerializedName("id")
        private int mId;

        @SerializedName("author")
        private String mAuthor;

        public static ReplyTo objectFromData(String str) {
            return new Gson().fromJson(str, ReplyTo.class);
        }

        public static ReplyTo objectFromData(String str, String key) {
            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ReplyTo.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public int getStatus() {
            return mStatus;
        }

        public void setStatus(int status) {
            mStatus = status;
        }

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public void setAuthor(String author) {
            mAuthor = author;
        }
    }
}
