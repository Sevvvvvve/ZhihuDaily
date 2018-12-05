package com.vaynefond.zhihudaily.data.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 新闻对应短评论查看
 * URL: https://news-at.zhihu.com/api/4/story/4232852/short-comments
 *
 * 使用在 最新消息 中获得的 id，在 https://news-at.zhihu.com/api/4/story/#{id}/short-comments 中将 id 替换为对应的 id，得到短评论 JSON 格式的内容
 *
 * 响应实例：
 *
 *   {
 *       "comments": [
 *           {
 *               "author": "Xiaole说",
 *               "id": 545721,
 *               "content": "就吃了个花生米，呵呵",
 *               "likes": 0,
 *               "time": 1413600071,
 *               "avatar": "http://pic1.zhimg.com/c41f035ab_im.jpg"
 *           },
 *           ...
 *       ]
 *   }
 * 格式与前同，恕不再赘述
 */
public class ShortComments {

    @SerializedName("comments")
    private List<Comment> mComments;

    public static ShortComments objectFromData(String str) {
        return new Gson().fromJson(str, ShortComments.class);
    }

    public static ShortComments objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), ShortComments.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }
}
