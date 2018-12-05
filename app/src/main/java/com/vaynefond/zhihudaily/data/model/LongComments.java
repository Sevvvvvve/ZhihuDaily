package com.vaynefond.zhihudaily.data.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 新闻对应长评论查看
 * URL: https://news-at.zhihu.com/api/4/story/8997528/long-comments
 *
 * 使用在 最新消息 中获得的 id，在 https://news-at.zhihu.com/api/4/story/#{id}/long-comments 中将 id 替换为对应的 id，得到长评论 JSON 格式的内容
 *
 * 响应实例：
 *
 *   {
 *       "comments": [
 *           {
 *                "author":"巨型黑娃儿",
 *                "content":"也不算逻辑问题。其实小时候刚刚听说这个玩意的时候我也奇...",
 *                "avatar":"http://pic3.zhimg.com/4131a3385c748c9e2d02ab80e29a0c52_im.jpg",
 *                "time":1479706360,
 *                "reply_to":{
 *                            "content":"第二个机灵抖的还是有逻辑问题，不该说忘了，应该说没喝过啊我也不知道",
 *                            "status":0,
 *                            "id":27275308,
 *                            "author":"2233155495"
 *                            },
 *                "id":27276057,
 *                "likes":2
 *           },
 *           ...
 *       ]
 *   }
 * 分析：
 *
 * comments : 长评论列表，形式为数组（请注意，其长度可能为 0）
 * author : 评论作者
 * content : 评论的内容
 * avatar : 用户头像图片的地址
 * id : 评论者的唯一标识符
 * likes : 评论所获『赞』的数量
 * time : 评论时间
 * reply_to : 所回复的消息
 * content : 原消息的内容
 * status : 消息状态，0为正常，非0为已被删除
 * id : 被回复者的唯一标识符
 * author : 被回复者
 * err_msg: 错误消息，仅当status非0时出现
 */

public class LongComments {

    @SerializedName("comments")
    private List<Comment> mComments;

    public static LongComments objectFromData(String str) {

        return new Gson().fromJson(str, LongComments.class);
    }

    public static LongComments objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), LongComments.class);
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
