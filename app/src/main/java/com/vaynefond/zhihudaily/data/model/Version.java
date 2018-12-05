package com.vaynefond.zhihudaily.data.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Version {

    /**
     * status : 1
     * msg : 【更新】 - 极大提升性能及稳定性 - 部分用户无法使用新浪微博登录 - 部分用户无图模式无法分享至微信及朋友圈
     * url : http://zhstatic.zhihu.com/pkg/store/daily/zhihu-daily-zhihu-2.6.0(744)-release.apk
     * latest : 2.6.0
     */

    @SerializedName("status")
    private int mStatus;

    @SerializedName("msg")
    private String mMsg;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("latest")
    private String mLatest;

    public static Version objectFromData(String str) {
        return new Gson().fromJson(str, Version.class);
    }

    public static Version objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Version.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getLatest() {
        return mLatest;
    }

    public void setLatest(String latest) {
        mLatest = latest;
    }
}
