package com.vaynefond.zhihudaily.data.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Editor {
    /**
     * url : http://www.zhihu.com/people/wezeit
     * bio : 微在 Wezeit 主编
     * id : 70
     * avatar : http://pic4.zhimg.com/068311926_m.jpg
     * name : 益康糯米
     */

    @SerializedName("url")
    private String mUrl;

    @SerializedName("bio")
    private String mBio;

    @SerializedName("id")
    private int mId;

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("name")
    private String mName;

    public static Editor objectFromData(String str) {
        return new Gson().fromJson(str, Editor.class);
    }

    public static Editor objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getString(str), Editor.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
