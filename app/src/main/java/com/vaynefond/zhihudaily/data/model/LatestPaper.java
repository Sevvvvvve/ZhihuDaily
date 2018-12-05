package com.vaynefond.zhihudaily.data.model;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LatestPaper {

    /**
     * date : 20180830
     * stories : [{"images":["https://pic4.zhimg.com/v2-f0d9898b37c0d2f55815b3575fb2add3.jpg"],"type":0,"id":9694388,"ga_prefix":"083019","title":"用吹风机吹个头发，里面门道还挺多的"},{"images":["https://pic3.zhimg.com/v2-167845cfa14953b872fec6c4b47bd5fa.jpg"],"type":0,"id":9693796,"ga_prefix":"083018","title":"茴香，京城饺子界的半壁江山"},{"images":["https://pic2.zhimg.com/v2-34c2dbd1e43c9dcce1dbe63b3e9bac5d.jpg"],"type":0,"id":9693959,"ga_prefix":"083016","title":"如果说「谁看起来最不像个地球人」，他当之无愧"},{"images":["https://pic2.zhimg.com/v2-adcab0ca5fef45b0fa89150ffa69a011.jpg"],"type":0,"id":9694557,"ga_prefix":"083015","title":"人肉搜索可怕吗？其实现在可以攻击你的手段早就升级了"},{"images":["https://pic4.zhimg.com/v2-ee5756b432049bfc4b81a145ce78283f.jpg"],"type":0,"id":9694555,"ga_prefix":"083013","title":"自如这样的长租公寓是怎样抬高房租的？举一个盒饭的例子你就懂了"},{"images":["https://pic1.zhimg.com/v2-18d220b54233db9567069cd86984468c.jpg"],"type":0,"id":9694450,"ga_prefix":"083012","title":"大误 · 人家成功人士每天只睡 4 个小时"},{"images":["https://pic2.zhimg.com/v2-00ccfe095b930ad8f7380e1b8d039ed9.jpg"],"type":0,"id":9694061,"ga_prefix":"083010","title":"你天生就能喝酒？来，到我这儿测一下才算数"},{"images":["https://pic4.zhimg.com/v2-b06b88e1c030076b9a9c62437ed027a7.jpg"],"type":0,"id":9693942,"ga_prefix":"083009","title":"- 这站到了停不停？ - 看我心情鸭"},{"title":"呃\u2026\u2026游戏里的人，为啥都不会脱衣服？","ga_prefix":"083008","images":["https://pic4.zhimg.com/v2-a7e6b4e11642fe160b6f6edd1e5bedf3.jpg"],"multipic":true,"type":0,"id":9694544},{"images":["https://pic2.zhimg.com/v2-7fce9ce0d4f5435e352030eea7d2c401.jpg"],"type":0,"id":9694537,"ga_prefix":"083007","title":"怎样搜集资料才能真正高效、有用？"},{"title":"看完这款游戏的演示视频之后，只想说一句：买就完了","ga_prefix":"083007","images":["https://pic4.zhimg.com/v2-3d2e2f206afbd680596f494717221697.jpg"],"multipic":true,"type":0,"id":9694501},{"images":["https://pic3.zhimg.com/v2-37879c5b2038d5be0e0c20e0613677c2.jpg"],"type":0,"id":9694520,"ga_prefix":"083007","title":"飞机要落地发现轮子丢了，这事不止「捡条人命」这么简单"},{"images":["https://pic4.zhimg.com/v2-ea829a9c9e445f4cade3bd52b475a103.jpg"],"type":0,"id":9694412,"ga_prefix":"083006","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic2.zhimg.com/v2-07911a9a9d4946d10b9811524c535bb5.jpg","type":0,"id":9694555,"ga_prefix":"083013","title":"自如这样的长租公寓是怎样抬高房租的？举一个盒饭的例子你就懂了"},{"image":"https://pic3.zhimg.com/v2-02dac6bd23cfb55643420a47652429c6.jpg","type":0,"id":9694544,"ga_prefix":"083008","title":"呃\u2026\u2026游戏里的人，为啥都不会脱衣服？"},{"image":"https://pic3.zhimg.com/v2-d822134dc65b01fbef28ef3ab162584e.jpg","type":0,"id":9694534,"ga_prefix":"082918","title":"中国队拿下亚运会 LOL 冠军，不枉我一直守着广播「看」比赛了"},{"image":"https://pic4.zhimg.com/v2-5471e3a2f6e6bdc465fbc4fafec12e83.jpg","type":0,"id":9694366,"ga_prefix":"082921","title":"今晚点映 · 谢谢你带她来过这个世界，给了我最好的童年"},{"image":"https://pic4.zhimg.com/v2-9f2ac4b197dd4297304c996030831927.jpg","type":0,"id":9694448,"ga_prefix":"082911","title":"华住 2 亿条开房记录被泄露，或许你还有一丝挽救的机会"}]
     */

    @SerializedName("date")
    private String mDate;

    @SerializedName("stories")
    private List<Story> mStories;

    @SerializedName("top_stories")
    private List<TopStory> mTopStories;

    public static LatestPaper objectFromData(String str) {
        return new Gson().fromJson(str, LatestPaper.class);
    }

    public static LatestPaper objectFromData(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return new Gson().fromJson(jsonObject.getString(str), LatestPaper.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static LatestPaper create(String date, @NonNull List<Story> stories, @NonNull List<TopStory> topStories) {
        LatestPaper paper = new LatestPaper();
        paper.setDate(date);
        paper.setStories(stories);
        paper.setTopStories(topStories);

        return paper;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public List<Story> getStories() {
        return mStories;
    }

    public void setStories(List<Story> stories) {
        mStories = stories;
    }

    public List<TopStory> getTopStories() {
        return mTopStories;
    }

    public void setTopStories(List<TopStory> topStories) {
        mTopStories = topStories;
    }

    public Paper getPaper() {
        return Paper.create(mDate, mStories);
    }
}
